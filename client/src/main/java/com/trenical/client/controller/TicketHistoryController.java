package com.trenical.client.controller;

import com.trenical.client.model.Ticket;
import com.trenical.client.session.SessionManager;
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

    @FXML private TableView<Ticket> tableView;
    @FXML private TableColumn<Ticket, String> colPartenza;
    @FXML private TableColumn<Ticket, String> colArrivo;
    @FXML private TableColumn<Ticket, String> colData;
    @FXML private TableColumn<Ticket, String> colOrario;
    @FXML private TableColumn<Ticket, String> colStato;
    @FXML private TableColumn<Ticket, String> colPrezzo;

    private ManagedChannel channel;

    @FXML
    public void initialize() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        caricaStoricoBiglietti();
    }

    private void caricaStoricoBiglietti() {
        String userId = SessionManager.getInstance().getCurrentUser().getUserId();

        try {
            var stub = TrenicalServiceGrpc.newBlockingStub(channel);
            StoricoBigliettiResponse response = stub.getStoricoBiglietti(
                    UserIdRequest.newBuilder().setUserId(userId).build()
            );

            ObservableList<Ticket> tickets = FXCollections.observableArrayList();
            for (BigliettoInfo info : response.getBigliettiList()) {
                tickets.add(new Ticket(
                        info.getId(),
                        info.getPartenza() + " → " + info.getArrivo(),
                        info.getTimestamp(),
                        info.getOrario(),
                        "Confermato",
                        info.getPrezzo(),
                        info.getPartenza(),
                        info.getArrivo()
                ));
            }

            if (tickets.isEmpty()) {
                showAlert("Nessun biglietto trovato per l'utente: " + userId);
                tableView.getItems().clear();
                return;
            }

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
        colPartenza.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getPartenza()));
        colArrivo.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getArrivo()));
        colData.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getData()));
        colOrario.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getOrario()));
        colStato.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getStato()));
        colPrezzo.setCellValueFactory(t ->
                new SimpleStringProperty("€ " + String.format("%.2f", t.getValue().getPrezzo()))
        );

        tableView.getSortOrder().setAll(colData);
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}
