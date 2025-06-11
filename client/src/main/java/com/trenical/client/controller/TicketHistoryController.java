package com.trenical.client.controller;

import com.trenical.client.model.Ticket;
import common.BigliettoInfo;
import common.StoricoBigliettiResponse;
import common.TrattaServiceGrpc;
import common.UserIdRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTicketId()));
        colTratta.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTratta()));
        colData.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getData()));
        colStato.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStato()));
    }

    @FXML
    public void onLoadTickets() {
        String userId = userIdField.getText().trim();
        if (userId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Inserisci un ID valido.").show();
            return;
        }

        System.out.println("📥 Caricamento storico per utente: " + userId);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();

        TrattaServiceGrpc.TrattaServiceBlockingStub stub = TrattaServiceGrpc.newBlockingStub(channel);

        try {
            UserIdRequest request = UserIdRequest.newBuilder().setUserId(userId).build();
            StoricoBigliettiResponse response = stub.getStoricoBiglietti(request);

            ObservableList<Ticket> lista = FXCollections.observableArrayList();

            for (BigliettoInfo info : response.getBigliettiList()) {
                Ticket ticket = new Ticket(
                        info.getId(),
                        info.getPartenza() + " → " + info.getArrivo(),
                        info.getTimestamp(),
                        "Confermato"
                );
                lista.add(ticket);
            }

            System.out.println("🟢 Biglietti trovati: " + lista.size());
            tableView.setItems(lista);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Errore durante il caricamento: " + e.getMessage()).show();
            e.printStackTrace();
        } finally {
            channel.shutdownNow();
        }
    }
}
