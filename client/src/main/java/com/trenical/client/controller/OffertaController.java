package com.trenical.client.controller;

import com.trenical.client.session.SessionManager;
import com.trenical.grpc.GetOffertaRequest;
import com.trenical.grpc.OffertaResponse;
import com.trenical.grpc.PromotionServiceGrpc;
import com.trenical.grpc.Tratta;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class OffertaController {

    @FXML private Label tipoLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label descrizioneLabel;

    private Tratta tratta;

    public void setContesto(Tratta grpcTratta) {
        this.tratta = grpcTratta;
        calcolaOfferta();
    }

    private void calcolaOfferta() {
        if (tratta == null) {
            showAlert("Errore", "Nessuna tratta selezionata");
            return;
        }

        var user = SessionManager.getInstance().getCurrentUser();
        if (user == null) {
            showAlert("Sessione mancante", "Effettua il login per visualizzare offerte");
            return;
        }

        ManagedChannel channel = null;

        try {
            channel = ManagedChannelBuilder
                    .forAddress("localhost", 50051)
                    .usePlaintext()
                    .build();

            var stub = PromotionServiceGrpc.newBlockingStub(channel);
            OffertaResponse response = stub.getOfferta(
                    GetOffertaRequest.newBuilder()
                            .setUserId(user.getUserId())
                            .setTratta(tratta)
                            .build()
            );

            tipoLabel.setText(response.getTipo());
            prezzoLabel.setText("€ " + String.format("%.2f", response.getPrezzoScontato()));
            descrizioneLabel.setText(response.getDescrizione());

        } catch (Exception e) {
            tipoLabel.setText("-");
            prezzoLabel.setText("-");
            descrizioneLabel.setText("Errore durante il calcolo: " + e.getMessage());
        } finally {
            if (channel != null && !channel.isShutdown()) {
                channel.shutdownNow();
            }
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setTitle(title);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
