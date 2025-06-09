package com.trenical.server.controller;

import com.trenical.server.model.Treno;
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
        codiceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCodice()));
        partenzaColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPartenza()));
        arrivoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getArrivo()));
        orarioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOrario()));
        trenoTable.setItems(treni);
    }

    @FXML
    public void aggiungiTreno() {
        String codice = codiceField.getText();
        String partenza = partenzaField.getText();
        String arrivo = arrivoField.getText();
        String orario = orarioField.getText();

        if (!codice.isEmpty() && !partenza.isEmpty() && !arrivo.isEmpty() && !orario.isEmpty()) {
            Treno treno = new Treno(codice, partenza, arrivo, orario);
            treni.add(treno);
            codiceField.clear();
            partenzaField.clear();
            arrivoField.clear();
            orarioField.clear();
        }
    }
}
