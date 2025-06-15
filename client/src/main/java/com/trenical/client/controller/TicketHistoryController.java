package com.trenical.client.controller;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TicketHistoryController {

    @FXML private TextField userIdField;
    @FXML private TableView<Ticket> tableView;
    @FXML private TableColumn<Ticket, String> colId;
    @FXML private TableColumn<Ticket, String> colTratta;
    @FXML private TableColumn<Ticket, String> colData;
    @FXML private TableColumn<Ticket, String> colStato;
    @FXML private TableColumn<Ticket, String> colPrezzo;

    @FXML
    public void onLoadTickets() {
        String userId = userIdField.getText().trim();
        if (userId.isEmpty()) {
            showAlert("Inserisci un ID utente valido.");
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TrenicalServiceGrpc.TrenicalServiceBlockingStub stub = TrenicalServiceGrpc.newBlockingStub(channel);

        UserIdRequest request = UserIdRequest.newBuilder().setUserId(userId).build();
        StoricoBigliettiResponse response = stub.getStoricoBiglietti(request);

        ObservableList<Ticket> tickets = FXCollections.observableArrayList();

        for (BigliettoInfo info : response.getBigliettiList()) {
            tickets.add(new Ticket(
                    info.getId(),
                    info.getTrattaId(),
                    info.getTimestamp(),
                    "Confermato",
                    info.getPrezzo()
            ));
        }

        setupColumns(); // inizializzazione colonne
        tableView.setItems(tickets);

        channel.shutdown();
    }

    private void setupColumns() {
        colId.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getTicketId()));
        colTratta.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getTratta()));
        colData.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getData()));
        colStato.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getStato()));
        colPrezzo.setCellValueFactory(t -> new SimpleStringProperty("€ " + String.format("%.2f", t.getValue().getPrezzo())));
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}
