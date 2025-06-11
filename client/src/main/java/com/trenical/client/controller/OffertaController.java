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

    private Tratta tratta;
    private ManagedChannel channel;

    public void setContesto(Tratta tratta) {
        this.tratta = tratta;
        calcolaOfferta();
    }

    private void calcolaOfferta() {
        if (tratta == null) {
            showAlert("Errore", "Nessuna tratta fornita");
            return;
        }

        String userId = SessionManager.getInstance().getCurrentUser().getUserId();
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        PromotionServiceGrpc.PromotionServiceBlockingStub stub =
                PromotionServiceGrpc.newBlockingStub(channel);

        try {
            OffertaResponse response = stub.getOfferta(
                    GetOffertaRequest.newBuilder()
                            .setUserId(userId)
                            .setTratta(tratta)
                            .build()
            );
            prezzoLabel.setText("€ " + response.getPrezzoScontato());
            descrizioneLabel.setText(response.getDescrizione());
        } catch (Exception e) {
            prezzoLabel.setText("-");
            descrizioneLabel.setText("Errore durante il calcolo");
            showAlert("Errore", e.getMessage());
        } finally {
            if (channel != null && !channel.isShutdown()) {
                channel.shutdownNow();
            }
        }
    }

    @FXML
    private Label prezzoLabel;
    @FXML private Label descrizioneLabel;

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
