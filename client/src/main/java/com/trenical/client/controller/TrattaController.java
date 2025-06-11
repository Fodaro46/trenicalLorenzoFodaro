package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import common.AcquistaBigliettoRequest;
import common.AcquistaBigliettoResponse;
import common.CercaTratteRequest;
import common.CercaTratteResponse;
import common.TrattaServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class TrattaController {

    @FXML private TextField partenzaField;
    @FXML private TextField arrivoField;
    @FXML private TableView<Tratta> tableView;
    @FXML private TableColumn<Tratta, String> colPartenza;
    @FXML private TableColumn<Tratta, String> colArrivo;
    @FXML private TableColumn<Tratta, String> colOrarioPartenza;
    @FXML private TableColumn<Tratta, String> colOrarioArrivo;
    @FXML private TableColumn<Tratta, Double> colPrezzo;

    private final ObservableList<Tratta> trattaList = FXCollections.observableArrayList();
    private ManagedChannel channel;
    private TrattaServiceGrpc.TrattaServiceBlockingStub stub;

    @FXML
    public void initialize() {
        // Configura le colonne
        colPartenza.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStazionePartenza()));
        colArrivo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStazioneArrivo()));
        colOrarioPartenza.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getOrarioPartenza()));
        colOrarioArrivo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getOrarioArrivo()));
        colPrezzo.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrezzo()).asObject());

        tableView.setItems(trattaList);

        // Inizializza gRPC
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        stub = TrattaServiceGrpc.newBlockingStub(channel);

        // Chiudi il canale quando la finestra viene chiusa
        Platform.runLater(() -> {
            Window window = tableView.getScene().getWindow();
            window.setOnHidden(e -> shutdown());
        });
    }

    @FXML
    private void onCercaTratte() {
        String partenza = partenzaField.getText().trim();
        String arrivo = arrivoField.getText().trim();

        if (partenza.isEmpty() || arrivo.isEmpty()) {
            mostraErrore("Inserisci entrambe le stazioni.");
            return;
        }

        CercaTratteRequest request = CercaTratteRequest.newBuilder()
                .setStazionePartenza(partenza)
                .setStazioneArrivo(arrivo)
                .build();

        try {
            CercaTratteResponse response = stub.cercaTratte(request);
            trattaList.clear();

            if (response.getTratteList().isEmpty()) {
                mostraErrore("Nessuna tratta trovata.");
                return;
            }

            response.getTratteList().forEach(t -> trattaList.add(new Tratta(
                    t.getId(),
                    t.getStazionePartenza(),
                    t.getStazioneArrivo(),
                    t.getOrarioPartenza(),
                    t.getOrarioArrivo(),
                    t.getPrezzo()
            )));
        } catch (Exception e) {
            mostraErrore("Errore di connessione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onAcquista() {
        Tratta selected = getSelectedTratta();
        if (selected == null) {
            mostraErrore("Seleziona una tratta da acquistare.");
            return;
        }

        AcquistaBigliettoRequest request = AcquistaBigliettoRequest.newBuilder()
                .setIdTratta(selected.getId())
                .build();

        try {
            AcquistaBigliettoResponse response = stub.acquistaBiglietto(request);
            Alert info = new Alert(Alert.AlertType.INFORMATION, response.getMessaggio());
            info.setHeaderText(null);
            info.showAndWait();
        } catch (Exception e) {
            mostraErrore("Errore durante l'acquisto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /** Permette a MainController di recuperare la tratta selezionata */
    public Tratta getSelectedTratta() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    /** Chiude il canale gRPC */
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdownNow();
        }
    }
}
