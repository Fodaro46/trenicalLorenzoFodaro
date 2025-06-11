package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import common.AcquistaBigliettoRequest;
import common.AcquistaBigliettoResponse;
import common.TrattaServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AcquistoController {

    @FXML private Label trattaLabel;
    @FXML private Label prezzoLabel;
    @FXML private DatePicker dataPicker;

    private Tratta tratta;

    private ManagedChannel channel;
    private TrattaServiceGrpc.TrattaServiceBlockingStub stub;

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
        trattaLabel.setText(tratta.getStazionePartenza() + " → " + tratta.getStazioneArrivo() +
                " (" + tratta.getOrarioPartenza() + " - " + tratta.getOrarioArrivo() + ")");
        prezzoLabel.setText("€ " + tratta.getPrezzo());
    }

    @FXML
    public void initialize() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();
        stub = TrattaServiceGrpc.newBlockingStub(channel);
    }

    @FXML
    public void onAcquista() {
        String userId = SessionManager.getInstance().getCurrentUser().getUserId();
        if (userId == null) {
            showError("Effettua il login prima di acquistare.");
            return;
        }

        String data = dataPicker.getValue() != null ? dataPicker.getValue().toString() : "";
        if (data.isEmpty()) {
            showError("Inserisci la data del viaggio.");
            return;
        }

        AcquistaBigliettoRequest request = AcquistaBigliettoRequest.newBuilder()
                .setUserId(userId)
                .setIdTratta(tratta.getId())
                .setData(data)
                .build();

        try {
            AcquistaBigliettoResponse response = stub.acquistaBiglietto(request);
            showInfo(response.getMessaggio());
            closeWindow();
        } catch (Exception e) {
            showError("Errore durante l'acquisto: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) dataPicker.getScene().getWindow();
        stage.close();
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}