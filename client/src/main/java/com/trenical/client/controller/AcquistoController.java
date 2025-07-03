package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AcquistoController {

    @FXML private Label trattaLabel;
    @FXML private Label dataLabel;
    @FXML private Label orarioLabel;
    @FXML private Label prezzoLabel;

    private Tratta tratta;
    private double prezzoFinale;
    private ManagedChannel channel;

    @FXML
    public void initialize() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        // Chiude il canale se l’utente chiude la finestra senza acquistare
        try {
            trattaLabel.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.windowProperty().addListener((obsW, oldW, newW) -> {
                        if (newW != null) {
                            ((Stage) newW).setOnHiding(e -> shutdown());
                        }
                    });
                }
            });
        } catch (Exception ignored) {}
    }

    public void setTratta(Tratta t) {
        this.tratta = t;
        trattaLabel.setText(t.getStazionePartenza() + " → " + t.getStazioneArrivo());
        dataLabel.setText("Data: " + t.getData());
        orarioLabel.setText("Orario: " + t.getOrarioPartenza());
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
                                    .setData(t.getData())
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
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDate data = LocalDate.parse(tratta.getData());
            LocalTime orario = LocalTime.parse(tratta.getOrarioPartenza());
            LocalDateTime partenza = LocalDateTime.of(data, orario);

            if (partenza.isBefore(now)) {
                new Alert(Alert.AlertType.ERROR,
                        "Non puoi acquistare biglietti per un orario già passato.")
                        .showAndWait();
                return;
            }

            var stub = TrenicalServiceGrpc.newBlockingStub(channel);
            stub.acquistaBiglietto(
                    BigliettoRequest.newBuilder()
                            .setUserId(SessionManager.getInstance().getCurrentUser().getUserId())
                            .setTratta(tratta.getId())
                            .setData(tratta.getData())
                            .setOrario(tratta.getOrarioPartenza())
                            .build()
            );

            new Alert(Alert.AlertType.INFORMATION,
                    "Acquisto confermato!\nPrezzo finale: € " + String.format("%.2f", prezzoFinale))
                    .showAndWait();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Errore durante l'acquisto: " + ex.getMessage()).showAndWait();
        } finally {
            shutdown();
            Stage currentStage = (Stage) trattaLabel.getScene().getWindow();
            currentStage.close();
        }
    }


    private void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdownNow();
        }
    }
}
