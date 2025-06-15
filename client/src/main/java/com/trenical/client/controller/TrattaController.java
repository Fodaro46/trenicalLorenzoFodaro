package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
    private TrenicalServiceGrpc.TrenicalServiceBlockingStub stub;

    @FXML
    public void initialize() {
        // Setup tabella
        colPartenza.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStazionePartenza()));
        colArrivo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStazioneArrivo()));
        colOrarioPartenza.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrarioPartenza()));
        colOrarioArrivo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrarioArrivo()));
        colPrezzo.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrezzo()).asObject());

        tableView.setItems(trattaList);

        // Avvio canale gRPC
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        stub = TrenicalServiceGrpc.newBlockingStub(channel);

        // Chiudi il canale gRPC alla chiusura finestra
        tableView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obsWin, oldWin, newWin) -> {
                    if (newWin != null) {
                        newWin.setOnHiding(e -> {
                            if (channel != null && !channel.isShutdown()) {
                                channel.shutdownNow();
                                System.out.println("🔌 Canale gRPC chiuso da TrattaController");
                            }
                        });
                    }
                });
            }
        });
    }

    @FXML
    private void onCercaTratte() {
        String partenza = partenzaField.getText().trim();
        String arrivo = arrivoField.getText().trim();

        if (partenza.isEmpty() || arrivo.isEmpty()) {
            showAlert("Inserisci entrambe le stazioni.");
            return;
        }

        CercaTratteRequest req = CercaTratteRequest.newBuilder()
                .setStazionePartenza(partenza)
                .setStazioneArrivo(arrivo)
                .setData("")  // eventualmente usabile in futuro
                .build();

        try {
            CercaTratteResponse resp = stub.cercaTratte(req);
            trattaList.setAll(resp.getTratteList().stream().map(t ->
                    new Tratta(t.getId(), t.getStazionePartenza(), t.getStazioneArrivo(),
                            t.getOrarioPartenza(), t.getOrarioArrivo(), t.getPrezzo())
            ).toList());

            if (trattaList.isEmpty()) {
                showAlert("Nessuna tratta trovata.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Errore di connessione: " + ex.getMessage());
        }
    }

    @FXML
    private void onAcquista() {
        Tratta sel = tableView.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Seleziona prima una tratta.");
            return;
        }
        if (!SessionManager.getInstance().isLoggedIn()) {
            showAlert("Effettua il login prima di acquistare.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acquisto-view.fxml"));
            Pane pane = loader.load();
            AcquistoController ctrl = loader.getController();
            ctrl.setTratta(sel);

            Stage stage = new Stage();
            stage.setTitle("Acquista Biglietto");
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Impossibile aprire la finestra di acquisto.");
        }
    }

    public Tratta getSelectedTratta() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}
