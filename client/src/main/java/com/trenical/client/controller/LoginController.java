package com.trenical.client.controller;

import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private Label resultLabel;

    @FXML
    public void onLogin() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            resultLabel.setText("Inserisci un'email valida.");
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TrenicalServiceGrpc.TrenicalServiceBlockingStub stub = TrenicalServiceGrpc.newBlockingStub(channel);

        LoginRequest request = LoginRequest.newBuilder()
                .setEmail(email)
                .build();

        LoginResponse response = stub.login(request);

        resultLabel.setText("Utente: " + response.getUserId() + "\n" + response.getMessage());

        channel.shutdown();
    }
}
