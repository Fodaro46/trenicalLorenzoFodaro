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

import java.util.Comparator;

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

        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                    .usePlaintext()
                    .build();
            var stub = TrenicalServiceGrpc.newBlockingStub(channel);

            StoricoBigliettiResponse response = stub.getStoricoBiglietti(
                    UserIdRequest.newBuilder().setUserId(userId).build()
            );

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

            if (tickets.isEmpty()) {
                showAlert("Nessun biglietto trovato per l'utente: " + userId);
                tableView.getItems().clear();
                return;
            }

            // Ordina per data decrescente (ISO yyyy-MM-dd)
            tickets.sort(Comparator.comparing(Ticket::getData).reversed());

            setupColumns();
            tableView.setItems(tickets);

        } catch (Exception e) {
            showAlert("Errore caricamento storico: " + e.getMessage());
        } finally {
            if (channel != null && !channel.isShutdown()) {
                channel.shutdownNow();
            }
        }
    }

    private void setupColumns() {
        colId.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getTicketId()));
        colTratta.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getTratta()));
        colData.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getData()));
        colStato.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getStato()));
        colPrezzo.setCellValueFactory(t ->
                new SimpleStringProperty("€ " + String.format("%.2f", t.getValue().getPrezzo()))
        );
        // Mostra in testa la colonna Data
        tableView.getSortOrder().setAll(colData);
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}
