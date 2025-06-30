package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.*;
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
    private double prezzoFinale;

    private ManagedChannel channel;

    @FXML
    public void initialize() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        dataPicker.sceneProperty().addListener((obs, old, scene) -> {
            if (scene != null) {
                scene.windowProperty().addListener((o2, wOld, wNew) -> wNew.setOnHiding(e -> {
                    if (channel != null && !channel.isShutdown()) {
                        channel.shutdownNow();
                    }
                }));
            }
        });
    }

    public void setTratta(Tratta t) {
        this.tratta = t;
        trattaLabel.setText(t.getStazionePartenza() + " → " + t.getStazioneArrivo());

        // Calcola subito il prezzo scontato
        calcolaPrezzoScontato(t);
    }

    private void calcolaPrezzoScontato(Tratta t) {
        try {
            var stub = PromotionServiceGrpc.newBlockingStub(channel);

            OffertaResponse offerta = stub.getOfferta(
                    GetOffertaRequest.newBuilder()
                            .setUserId(SessionManager.getInstance().getCurrentUser().getUserId())
                            .setTratta(com.trenical.grpc.Tratta.newBuilder()
                                    .setId(t.getId())
                                    .setStazionePartenza(t.getStazionePartenza())
                                    .setStazioneArrivo(t.getStazioneArrivo())
                                    .setOrarioPartenza(t.getOrarioPartenza())
                                    .setOrarioArrivo(t.getOrarioArrivo())
                                    .setPrezzo(t.getPrezzo())
                                    .setData("")
                                    .build())
                            .build()
            );

            prezzoFinale = offerta.getPrezzoScontato();
            prezzoLabel.setText("€ " + String.format("%.2f", prezzoFinale));

        } catch (Exception e) {
            prezzoFinale = t.getPrezzo();
            prezzoLabel.setText("€ " + String.format("%.2f", prezzoFinale));
        }
    }

    @FXML
    private void onConfirmAcquisto() {
        if (dataPicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Seleziona la data del viaggio.").showAndWait();
            return;
        }

        String data = dataPicker.getValue().toString();

        try {
            var stub = TrenicalServiceGrpc.newBlockingStub(channel);
            BigliettoResponse resp = stub.acquistaBiglietto(
                    BigliettoRequest.newBuilder()
                            .setUserId(SessionManager.getInstance().getCurrentUser().getUserId())
                            .setTratta(tratta.getId())
                            .setData(data)
                            .build()
            );

            new Alert(Alert.AlertType.INFORMATION,
                    "Acquisto confermato!\nPrezzo finale: € " + String.format("%.2f", prezzoFinale))
                    .showAndWait();

            dataPicker.getScene().getWindow().hide();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Errore durante l'acquisto: " + ex.getMessage()).showAndWait();
        }
    }
}