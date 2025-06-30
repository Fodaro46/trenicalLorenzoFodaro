package com.trenical.client.controller;

import com.trenical.client.model.User;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private Label resultLabel;

    private ManagedChannel channel;

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

            startNotificationStream(asyncStub, userId);

            Stage stage = (Stage) resultLabel.getScene().getWindow();
            stage.setOnHiding(ev -> {
                if (channel != null && !channel.isShutdown()) {
                    channel.shutdownNow();
                }
            });

        } catch (Exception e) {
            Platform.runLater(() -> resultLabel.setText("Errore durante il login: " + e.getMessage()));
        }
    }

    private void startNotificationStream(TrenicalServiceGrpc.TrenicalServiceStub asyncStub, String userId) {
        asyncStub.streamNotifiche(
                NotificheRequest.newBuilder().setUserId(userId).build(),
                new StreamObserver<>() {
                    @Override
                    public void onNext(Notifica n) {
                        SessionManager.getInstance().aggiungiNotifica(n);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println("Errore stream notifiche: " + t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Stream notifiche terminato per: " + userId);
                    }
                }
        );
    }
}
