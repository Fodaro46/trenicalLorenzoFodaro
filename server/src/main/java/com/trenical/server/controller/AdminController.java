package com.trenical.server.controller;

import com.trenical.grpc.Notifica;
import com.trenical.server.model.Tratta;
import com.trenical.server.model.Utente;
import com.trenical.server.repository.BigliettoRepository;
import com.trenical.server.repository.TrattaRepository;
import com.trenical.server.repository.UtenteRepository;
import com.trenical.server.util.NotificationRegistry;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @FXML
    public void initialize() {
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

        TrattaRepository.salvaTratte(tratte);
        trattaTable.refresh();
        clearFields();
        showInfo("Tratta aggiornata!");

        var tuttiIBiglietti = BigliettoRepository.caricaBiglietti();

        for (Utente u : UtenteRepository.caricaTutti()) {
            boolean haTratta = tuttiIBiglietti.stream()
                    .anyMatch(b -> b.getUserId().equals(u.getUserId()) && b.getTrattaId().equals(sel.getId()));

            if (haTratta) {
                Notifica n = Notifica.newBuilder()
                        .setUserId(u.getUserId())  // 🔥 FONDAMENTALE
                        .setMessaggio("🔄 Tratta aggiornata: " + sel.getStazionePartenza() + " → " + sel.getStazioneArrivo())
                        .setTimestamp(LocalDateTime.now().toString())
                        .build();

                NotificationRegistry.addNotification(u.getUserId(), n);
                System.out.println("📌 [LOG] Notifica inviata a " + u.getUserId() + ": " + n.getMessaggio());
            }
        }
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
}
