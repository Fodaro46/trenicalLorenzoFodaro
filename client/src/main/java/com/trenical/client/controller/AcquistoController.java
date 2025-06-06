package com.trenical.client.controller;

import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AcquistoController {

    @FXML private TextField userIdField;
    @FXML private TextField trattaField;
    @FXML private TextField dataField;
    @FXML private Label resultLabel;

    @FXML
    public void onAcquista() {
        String userId = userIdField.getText().trim();
        String tratta = trattaField.getText().trim();
        String data = dataField.getText().trim();

        if (userId.isEmpty() || tratta.isEmpty() || data.isEmpty()) {
            resultLabel.setText("Tutti i campi sono obbligatori.");
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TrenicalServiceGrpc.TrenicalServiceBlockingStub stub = TrenicalServiceGrpc.newBlockingStub(channel);

        BigliettoRequest request = BigliettoRequest.newBuilder()
                .setUserId(userId)
                .setTratta(tratta)
                .setData(data)
                .build();

        BigliettoResponse response = stub.acquistaBiglietto(request);

        resultLabel.setText("Biglietto ID: " + response.getBigliettoId() +
                "\nStato: " + response.getStato());

        channel.shutdown();
    }
}
