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

        TrenicalServiceGrpc.TrenicalServiceBlockingStub stub = TrenicalServiceGrpc.newBlockingStub(channel);
        TrenicalServiceGrpc.TrenicalServiceStub asyncStub = TrenicalServiceGrpc.newStub(channel);

        LoginRequest request = LoginRequest.newBuilder()
                .setEmail(email)
                .build();

        LoginResponse response = stub.login(request);

        // ✅ Salva l'utente nella sessione
        SessionManager.getInstance().login(new User(response.getUserId(), email));

        resultLabel.setText("Utente: " + response.getUserId() + "\n" + response.getMessage());

        // 📨 Ricevi notifiche
        NotificheRequest notifRequest = NotificheRequest.newBuilder()
                .setUserId(response.getUserId())
                .build();

        asyncStub.streamNotifiche(notifRequest, new StreamObserver<Notifica>() {
            @Override
            public void onNext(Notifica notifica) {
                Platform.runLater(() ->
                        resultLabel.setText("📩 " + notifica.getMessaggio() + " @ " + notifica.getTimestamp())
                );
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("❌ Errore stream: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("✅ Stream notifiche completato.");
            }
        });
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
