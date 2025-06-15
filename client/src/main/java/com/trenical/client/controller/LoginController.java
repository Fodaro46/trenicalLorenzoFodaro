package com.trenical.client.controller;

import com.trenical.client.model.User;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private Label resultLabel;

    private ManagedChannel channel;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    public void onLogin() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            resultLabel.setText("Inserisci un'email valida.");
            return;
        }

        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        var blockingStub = TrenicalServiceGrpc.newBlockingStub(channel);
        var asyncStub = TrenicalServiceGrpc.newStub(channel);

        try {
            LoginResponse response = blockingStub.login(LoginRequest.newBuilder().setEmail(email).build());

            String userId = response.getUserId();
            User user = new User(userId, email);
            SessionManager.getInstance().login(user);

            resultLabel.setWrapText(true);
            resultLabel.setText("Utente: " + email + "\n" + response.getMessage());

            resultLabel.setMouseTransparent(false);
            resultLabel.setOnMouseClicked(e -> {
                var content = new ClipboardContent();
                content.putString(email);
                javafx.scene.input.Clipboard.getSystemClipboard().setContent(content);
            });

            // ✅ Avvia stream notifiche gRPC
            startNotificationStream(asyncStub, userId);

            // ✅ Apri subito GUI notifiche (il SessionManager gestirà automaticamente il reinvio)
            Platform.runLater(this::apriNotificheGui);

            // 🔒 Chiudi canale gRPC alla chiusura finestra
            Stage stage = (Stage) resultLabel.getScene().getWindow();
            stage.setOnHiding(ev -> {
                if (channel != null && !channel.isShutdown()) {
                    LOGGER.info("🔻 Arresto canale gRPC client");
                    channel.shutdownNow();
                }
            });

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Errore durante il login", e);
            Platform.runLater(() -> resultLabel.setText("Errore durante il login: " + e.getMessage()));
        }
    }

    private void startNotificationStream(TrenicalServiceGrpc.TrenicalServiceStub asyncStub, String userId) {
        asyncStub.streamNotifiche(
                NotificheRequest.newBuilder().setUserId(userId).build(),
                new StreamObserver<>() {
                    @Override
                    public void onNext(Notifica n) {
                        LOGGER.info("🆔 ID notifica ricevuta: " + (n.getId().isBlank() ? "❌ VUOTO" : n.getId()));
                        LOGGER.info("📥 [CLIENT] Ricevuta notifica:");
                        LOGGER.info("     🧾 Messaggio: " + n.getMessaggio());
                        LOGGER.info("     🕒 Timestamp: " + n.getTimestamp());
                        LOGGER.info("     👤 userId: " + n.getUserId());

                        // Il SessionManager si occuperà automaticamente di:
                        // 1. Salvare la notifica
                        // 2. Notificare i listener attivi
                        // 3. Gestire il reinvio per i listener futuri
                        SessionManager.getInstance().aggiungiNotifica(n);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LOGGER.log(Level.SEVERE, "❌ Errore stream notifiche", t);
                    }

                    @Override
                    public void onCompleted() {
                        LOGGER.info("📴 [STREAM] Fine stream notifiche per userId: " + userId);
                    }
                }
        );

        LOGGER.info("🚀 [STREAM] Stream notifiche avviato per userId: " + userId);

        // 🔧 OPZIONALE: Se vuoi forzare un reinvio manuale per debug
        // Platform.runLater(() -> {
        //     try {
        //         Thread.sleep(2000); // Aspetta che la GUI si carichi
        //         SessionManager.getInstance().forzaReinvioNotifiche();
        //     } catch (InterruptedException ex) {
        //         Thread.currentThread().interrupt();
        //     }
        // });
    }

    private void apriNotificheGui() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/notifiche.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("📬 Notifiche");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Errore nel caricamento finestra notifiche", e);
        }
    }
}