package com.trenical.client.controller;

import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TicketController {

    @FXML private TextField ticketIdField;
    @FXML private TextArea resultArea;

    @FXML
    public void onSearchTicket() {
        String ticketId = ticketIdField.getText().trim();
        if (ticketId.isEmpty()) {
            resultArea.setText("Inserisci un ID valido.");
            return;
        }

        // Connessione gRPC
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TrenicalServiceGrpc.TrenicalServiceBlockingStub stub = TrenicalServiceGrpc.newBlockingStub(channel);

        TicketRequest request = TicketRequest.newBuilder()
                .setTicketId(ticketId)
                .build();

        TicketResponse response = stub.getTicketInfo(request);

        resultArea.setText("ID: " + response.getTicketId() +
                "\nPasseggero: " + response.getPassengerName() +
                "\nTreno: " + response.getTrainNumber() +
                "\nPartenza: " + response.getDepartureTime() +
                "\nArrivo: " + response.getArrivalTime());

        channel.shutdown();
    }
}
