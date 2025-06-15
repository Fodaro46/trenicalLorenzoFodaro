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

    @FXML private Label prezzoLabel;
    @FXML private Label descrizioneLabel;

    private Tratta tratta;

    public void setContesto(Tratta grpcTratta) {
        this.tratta = grpcTratta;
        calcolaOfferta();
    }

    private void calcolaOfferta() {
        if (tratta == null) {
            showAlert("Errore", "Nessuna tratta fornita");
            return;
        }

        String userId = SessionManager.getInstance().getCurrentUser().getUserId();

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        try {
            var stub = PromotionServiceGrpc.newBlockingStub(channel);
            OffertaResponse resp = stub.getOfferta(
                    GetOffertaRequest.newBuilder()
                            .setUserId(userId)
                            .setTratta(tratta)
                            .build()
            );
            prezzoLabel.setText("€ " + resp.getPrezzoScontato());
            descrizioneLabel.setText(resp.getDescrizione());
        } catch (Exception e) {
            prezzoLabel.setText("-");
            descrizioneLabel.setText("Errore durante il calcolo");
            showAlert("Errore promozione", e.getMessage());
        } finally {
            channel.shutdownNow(); // chiusura sicura
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setTitle(title);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
