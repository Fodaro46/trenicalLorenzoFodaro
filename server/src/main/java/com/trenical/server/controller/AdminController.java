package com.trenical.server.controller;

import com.google.protobuf.Empty;
import com.trenical.grpc.UpdateTrattaRequest;
import com.trenical.grpc.TrenicalServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import com.trenical.server.model.Tratta;
import com.trenical.server.repository.TrattaRepository;

public class AdminController {

    @FXML private TextField codiceField;
    @FXML private TextField partenzaField;
    @FXML private TextField arrivoField;
    @FXML private TextField orarioPartenzaField;
    @FXML private TextField orarioArrivoField;
    @FXML private TextField prezzoField;

    @FXML private TableView<Tratta> trattaTable;
    @FXML private TableColumn<Tratta, String> codiceColumn;
    @FXML private TableColumn<Tratta, String> partenzaColumn;
    @FXML private TableColumn<Tratta, String> arrivoColumn;
    @FXML private TableColumn<Tratta, String> orarioPartenzaColumn;
    @FXML private TableColumn<Tratta, String> orarioArrivoColumn;
    @FXML private TableColumn<Tratta, String> prezzoColumn;

    private final ObservableList<Tratta> tratte = FXCollections.observableArrayList();

    private ManagedChannel channel;
    private TrenicalServiceGrpc.TrenicalServiceBlockingStub blockingStub;

    @FXML
    public void initialize() {
        // inizializza il canale gRPC e lo stub
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        blockingStub = TrenicalServiceGrpc.newBlockingStub(channel);

        codiceColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getId()));
        partenzaColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getStazionePartenza()));
        arrivoColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getStazioneArrivo()));
        orarioPartenzaColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getOrarioPartenza()));
        orarioArrivoColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getOrarioArrivo()));
        prezzoColumn.setCellValueFactory(cd -> new SimpleStringProperty("€ " + cd.getValue().getPrezzo()));

        trattaTable.setItems(tratte);
        tratte.setAll(TrattaRepository.caricaTratte());

        trattaTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, sel) -> {
                    if (sel != null) {
                        codiceField.setText(sel.getId());
                        partenzaField.setText(sel.getStazionePartenza());
                        arrivoField.setText(sel.getStazioneArrivo());
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
        String orarioPartenza = orarioPartenzaField.getText().trim();
        String orarioArrivo = orarioArrivoField.getText().trim();
        String prezzoStr = prezzoField.getText().trim();

        if (codice.isEmpty() || partenza.isEmpty() || arrivo.isEmpty() ||
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
        Tratta nuova = new Tratta(codice, partenza, arrivo, orarioPartenza, orarioArrivo, prezzo);
        tratte.add(nuova);
        TrattaRepository.aggiungiTratta(nuova);
        clearFields();
        showInfo("Tratta aggiunta!");
    }

    @FXML
    public void aggiornaTratta() {
        Tratta sel = trattaTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Seleziona una tratta da modificare.");
            return;
        }
        String partenza = partenzaField.getText().trim();
        String arrivo = arrivoField.getText().trim();
        String orarioPartenza = orarioPartenzaField.getText().trim();
        String orarioArrivo = orarioArrivoField.getText().trim();
        String prezzoStr = prezzoField.getText().trim();

        double prezzo;
        try {
            prezzo = Double.parseDouble(prezzoStr);
        } catch (NumberFormatException e) {
            showAlert("Prezzo non valido.");
            return;
        }
        sel.setStazionePartenza(partenza);
        sel.setStazioneArrivo(arrivo);
        sel.setOrarioPartenza(orarioPartenza);
        sel.setOrarioArrivo(orarioArrivo);
        sel.setPrezzo(prezzo);

        // Chiamata gRPC al server per aggiornare la tratta e inviare notifiche
        com.trenical.grpc.Tratta grpcTratta = com.trenical.grpc.Tratta.newBuilder()
                .setId(sel.getId())
                .setStazionePartenza(partenza)
                .setStazioneArrivo(arrivo)
                .setOrarioPartenza(orarioPartenza)
                .setOrarioArrivo(orarioArrivo)
                .setPrezzo(prezzo)
                .build();

        UpdateTrattaRequest req = UpdateTrattaRequest.newBuilder()
                .setTratta(grpcTratta)
                .build();
        blockingStub.updateTratta(req);

        clearFields();
        showInfo("Tratta aggiornata!");
    }

    private void clearFields() {
        codiceField.clear();
        partenzaField.clear();
        arrivoField.clear();
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
