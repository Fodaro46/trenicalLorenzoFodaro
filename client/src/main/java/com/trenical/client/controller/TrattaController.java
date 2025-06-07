package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import common.AcquistaBigliettoRequest;
import common.AcquistaBigliettoResponse;
import common.CercaTratteRequest;
import common.CercaTratteResponse;
import common.TrattaServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TrattaController {

    @FXML
    private TextField partenzaField;

    @FXML
    private TextField arrivoField;

    @FXML
    private TableView<Tratta> tableView;

    @FXML
    private TableColumn<Tratta, String> colPartenza;

    @FXML
    private TableColumn<Tratta, String> colArrivo;

    @FXML
    private TableColumn<Tratta, String> colOrarioPartenza;

    @FXML
    private TableColumn<Tratta, String> colOrarioArrivo;

    @FXML
    private TableColumn<Tratta, Double> colPrezzo;

    private final ObservableList<Tratta> trattaList = FXCollections.observableArrayList();

    private TrattaServiceGrpc.TrattaServiceBlockingStub stub;
    private ManagedChannel channel;

    @FXML
    public void initialize() {
        // Colonne
        colPartenza.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStazionePartenza()));
        colArrivo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStazioneArrivo()));
        colOrarioPartenza.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrarioPartenza()));
        colOrarioArrivo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrarioArrivo()));
        colPrezzo.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrezzo()).asObject());

        tableView.setItems(trattaList);

        // Connessione gRPC
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        stub = TrattaServiceGrpc.newBlockingStub(channel);
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

            response.getTratteList().forEach(t -> trattaList.add(
                    new Tratta(
                            t.getId(),
                            t.getStazionePartenza(),
                            t.getStazioneArrivo(),
                            t.getOrarioPartenza(),
                            t.getOrarioArrivo(),
                            t.getPrezzo()
                    )
            ));

        } catch (Exception e) {
            mostraErrore("Errore di connessione al server: " + e.getMessage());
        }
    }

    @FXML
    private void onAcquista() {
        Tratta tratta = tableView.getSelectionModel().getSelectedItem();

        if (tratta == null) {
            mostraErrore("Seleziona una tratta da acquistare.");
            return;
        }

        AcquistaBigliettoRequest request = AcquistaBigliettoRequest.newBuilder()
                .setIdTratta(tratta.getId())
                .build();

        try {
            AcquistaBigliettoResponse response = stub.acquistaBiglietto(request);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acquisto Biglietto");
            alert.setHeaderText(null);
            alert.setContentText(response.getMessaggio());
            alert.showAndWait();

        } catch (Exception e) {
            mostraErrore("Errore durante l'acquisto: " + e.getMessage());
        }
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
