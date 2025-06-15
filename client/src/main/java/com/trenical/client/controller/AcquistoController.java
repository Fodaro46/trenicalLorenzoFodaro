package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.BigliettoRequest;
import com.trenical.grpc.BigliettoResponse;
import com.trenical.grpc.TrenicalServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class AcquistoController {

    @FXML private Label trattaLabel;
    @FXML private Label prezzoLabel;
    @FXML private DatePicker dataPicker;

    private Tratta tratta;
    private ManagedChannel channel;
    private TrenicalServiceGrpc.TrenicalServiceBlockingStub stub;

    @FXML
    public void initialize() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        stub = TrenicalServiceGrpc.newBlockingStub(channel);

        dataPicker.sceneProperty().addListener((obs, old, scene) -> {
            if (scene != null) {
                scene.windowProperty().addListener((o2, wOld, wNew) -> {
                    wNew.setOnHiding(e -> {
                        if (channel != null && !channel.isShutdown()) {
                            channel.shutdownNow();
                        }
                    });
                });
            }
        });
    }

    public void setTratta(Tratta t) {
        this.tratta = t;
        trattaLabel.setText(t.getStazionePartenza() + " → " + t.getStazioneArrivo());
        prezzoLabel.setText("€ " + t.getPrezzo());
    }

    @FXML
    private void onConfirmAcquisto() {
        if (dataPicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Seleziona la data del viaggio.").showAndWait();
            return;
        }

        String data = dataPicker.getValue().toString();

        try {
            BigliettoResponse resp = stub.acquistaBiglietto(
                    BigliettoRequest.newBuilder()
                            .setUserId(SessionManager.getInstance().getCurrentUser().getUserId())
                            .setTratta(tratta.getId())
                            .setData(data)
                            .build()
            );

            new Alert(Alert.AlertType.INFORMATION,
                    "Acquisto confermato!\nPrezzo: € " + String.format("%.2f", resp.getPrezzo()))
                    .showAndWait();

            dataPicker.getScene().getWindow().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Errore durante l'acquisto: " + ex.getMessage()).showAndWait();
        }
    }
}
