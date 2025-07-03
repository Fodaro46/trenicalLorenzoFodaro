package com.trenical.server.controller;

import com.trenical.grpc.UpdateTrattaRequest;
import com.trenical.grpc.TrenicalServiceGrpc;
import com.trenical.server.model.Tratta;
import com.trenical.server.model.Utente;
import com.trenical.server.repository.TrattaRepository;
import com.trenical.server.repository.UtenteRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminController {

    // Tratte
    @FXML private TextField codiceField;
    @FXML private TextField partenzaField;
    @FXML private TextField arrivoField;
    @FXML private TextField dataField;
    @FXML private TextField orarioPartenzaField;
    @FXML private TextField orarioArrivoField;
    @FXML private TextField prezzoField;

    @FXML private TableView<Tratta> trattaTable;
    @FXML private TableColumn<Tratta, String> codiceColumn;
    @FXML private TableColumn<Tratta, String> partenzaColumn;
    @FXML private TableColumn<Tratta, String> arrivoColumn;
    @FXML private TableColumn<Tratta, String> dataColumn;
    @FXML private TableColumn<Tratta, String> orarioPartenzaColumn;
    @FXML private TableColumn<Tratta, String> orarioArrivoColumn;
    @FXML private TableColumn<Tratta, String> prezzoColumn;

    private final ObservableList<Tratta> tratte = FXCollections.observableArrayList();

    // gRPC
    private ManagedChannel channel;
    private TrenicalServiceGrpc.TrenicalServiceBlockingStub blockingStub;

    @FXML
    public void initialize() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();
        blockingStub = TrenicalServiceGrpc.newBlockingStub(channel);

        codiceColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getId()));
        partenzaColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getStazionePartenza()));
        arrivoColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getStazioneArrivo()));
        dataColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getData()));
        orarioPartenzaColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getOrarioPartenza()));
        orarioArrivoColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getOrarioArrivo()));
        prezzoColumn.setCellValueFactory(cd -> new SimpleStringProperty("€ " + cd.getValue().getPrezzo()));
        trattaTable.setItems(tratte);
        tratte.setAll(TrattaRepository.caricaTratte());

        trattaTable.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                codiceField.setText(sel.getId());
                partenzaField.setText(sel.getStazionePartenza());
                arrivoField.setText(sel.getStazioneArrivo());
                dataField.setText(sel.getData());
                orarioPartenzaField.setText(sel.getOrarioPartenza());
                orarioArrivoField.setText(sel.getOrarioArrivo());
                prezzoField.setText(String.valueOf(sel.getPrezzo()));
            }
        });
    }

    @FXML
    public void aggiungiTratta() {
        String codice = codiceField.getText().trim();
        String partenza = partenzaField.getText().trim();
        String arrivo = arrivoField.getText().trim();
        String data = dataField.getText().trim();
        String orarioPartenza = orarioPartenzaField.getText().trim();
        String orarioArrivo = orarioArrivoField.getText().trim();
        String prezzoStr = prezzoField.getText().trim();

        if (codice.isEmpty() || partenza.isEmpty() || arrivo.isEmpty() || data.isEmpty() ||
                orarioPartenza.isEmpty() || orarioArrivo.isEmpty() || prezzoStr.isEmpty()) {
            showAlert("Compila tutti i campi.");
            return;
        }

        double prezzo;
        try {
            prezzo = Double.parseDouble(prezzoStr);
        } catch (NumberFormatException e) {
            showAlert("Prezzo non valido.");
            return;
        }

        Tratta nuova = new Tratta(codice, partenza, arrivo, data, orarioPartenza, orarioArrivo, prezzo);
        tratte.add(nuova);
        TrattaRepository.aggiungiTratta(nuova);
        clearFields();
        showInfo("Tratta aggiunta!");
    }

    private void clearFields() {
        codiceField.clear();
        partenzaField.clear();
        arrivoField.clear();
        dataField.clear();
        orarioPartenzaField.clear();
        orarioArrivoField.clear();
        prezzoField.clear();
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdownNow();
        }
    }
}
