package com.trenical.client.controller;

import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.trenical.client.model.Ticket;
import com.trenical.client.factory.TicketFactory;

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

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TrenicalServiceGrpc.TrenicalServiceBlockingStub stub = TrenicalServiceGrpc.newBlockingStub(channel);

        TicketRequest request = TicketRequest.newBuilder()
                .setTicketId(ticketId)
                .build();

        TicketResponse response = stub.getTicketInfo(request);
        Ticket ticket = TicketFactory.fromTicketResponse(response);

        resultArea.setText("ID: " + ticket.getTicketId() +
                "\nTratta: " + ticket.getTratta() +
                "\nData: " + ticket.getData() +
                "\nStato: " + ticket.getStato() +
                "\nPrezzo: € " + String.format("%.2f", ticket.getPrezzo()));

        channel.shutdown();
    }
}
