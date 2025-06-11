package com.trenical.server.controller;

import com.trenical.server.model.Treno;
import com.trenical.server.model.Tratta;
import com.trenical.server.repository.TrattaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminController {

    @FXML private TextField codiceField;
    @FXML private TextField partenzaField;
    @FXML private TextField arrivoField;
    @FXML private TextField orarioField;
    @FXML private TableView<Treno> trenoTable;
    @FXML private TableColumn<Treno, String> codiceColumn;
    @FXML private TableColumn<Treno, String> partenzaColumn;
    @FXML private TableColumn<Treno, String> arrivoColumn;
    @FXML private TableColumn<Treno, String> orarioColumn;

    private final ObservableList<Treno> treni = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        codiceColumn.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getCodice()));
        partenzaColumn.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getPartenza()));
        arrivoColumn.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getArrivo()));
        orarioColumn.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getOrario()));
        trenoTable.setItems(treni);
    }

    @FXML
    public void aggiungiTreno() {
        String codice  = codiceField.getText().trim();
        String partenza = partenzaField.getText().trim();
        String arrivo   = arrivoField.getText().trim();
        String orario   = orarioField.getText().trim();

        if (codice.isEmpty() || partenza.isEmpty() || arrivo.isEmpty() || orario.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Compila tutti i campi.").showAndWait();
            return;
        }

        // Aggiorno la tabella interna
        Treno t = new Treno(codice, partenza, arrivo, orario);
        treni.add(t);

        // Creo il modello Tratta e lo salvo su file
        Tratta trattaModel = new Tratta(
                codice,            // id della tratta
                partenza,          // stazionePartenza
                arrivo,            // stazioneArrivo
                orario,            // orarioPartenza
                orario,            // orarioArrivo (o aggiungi un TextField dedicato)
                20.0               // prezzo fisso per ora
        );
        TrattaRepository.aggiungiTratta(trattaModel);

        // Pulisco i campi
        codiceField.clear();
        partenzaField.clear();
        arrivoField.clear();
        orarioField.clear();

        new Alert(Alert.AlertType.INFORMATION, "Tratta aggiunta con successo!").showAndWait();
    }
}
