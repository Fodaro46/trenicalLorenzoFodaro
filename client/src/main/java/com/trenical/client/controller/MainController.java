package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML private StackPane contentPane;
    private TrattaController trattaController;

    private boolean checkLogin() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            Alert a = new Alert(Alert.AlertType.WARNING,
                    "Effettua il login prima di usare questa funzionalità.");
            a.setHeaderText("Accesso Richiesto");
            a.showAndWait();
            return false;
        }
        return true;
    }

    public void showLogin() {
        loadView("login-view.fxml");
    }

    public void showTratte() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Trattaview.fxml"));
            Pane pane = loader.load();
            trattaController = loader.getController();
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAcquisto() {
        if (!checkLogin()) return;
        if (trattaController == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Visita prima 'Ricerca Tratte'").showAndWait();
            return;
        }
        Tratta sel = trattaController.getSelectedTratta();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Seleziona una tratta.").showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/acquisto-view.fxml"));
            Pane pane = loader.load();
            AcquistoController ac = loader.getController();
            ac.setTratta(sel);
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOfferta() {
        if (!checkLogin()) return;
        if (trattaController == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Visita prima 'Ricerca Tratte'").showAndWait();
            return;
        }
        Tratta sel = trattaController.getSelectedTratta();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Seleziona una tratta.").showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/offerta.fxml"));
            Pane pane = loader.load();
            OffertaController oc = loader.getController();
            com.trenical.grpc.Tratta grpcT = com.trenical.grpc.Tratta.newBuilder()
                    .setId(sel.getId())
                    .setStazionePartenza(sel.getStazionePartenza())
                    .setStazioneArrivo(sel.getStazioneArrivo())
                    .setOrarioPartenza(sel.getOrarioPartenza())
                    .setOrarioArrivo(sel.getOrarioArrivo())
                    .setPrezzo(sel.getPrezzo())
                    .build();
            oc.setContesto(grpcT);
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTicket() {
        if (!checkLogin()) return;
        loadView("ticket-view.fxml");
    }

    public void showStorico() {
        if (!checkLogin()) return;
        loadView("ticket-history.fxml");
    }

    public void showNotifiche() {
        if (!checkLogin()) return;
        loadView("notifiche.fxml");
    }

    private void loadView(String fxml) {
        try {
            Pane p = FXMLLoader.load(
                    getClass().getResource("/" + fxml));
            contentPane.getChildren().setAll(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
