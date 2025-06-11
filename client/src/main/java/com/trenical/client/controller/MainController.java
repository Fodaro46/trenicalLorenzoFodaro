package com.trenical.client.controller;

import com.trenical.client.session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    private void setContent(String fxmlPath) {
        try {
            System.out.println("Trying to load: " + fxmlPath);
            var resource = getClass().getResource("/" + fxmlPath); // 🔥 SLASH AGGIUNTO
            if (resource == null) {
                System.err.println("❌ Resource not found: " + fxmlPath);
                return;
            }
            Pane pane = FXMLLoader.load(resource);
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLogin() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Accesso Richiesto");
            alert.setHeaderText("Devi essere loggato");
            alert.setContentText("Effettua prima il login per accedere a questa funzionalità.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void showLogin() {
        setContent("login-view.fxml");
    }

    public void showTratte() {
        setContent("Trattaview.fxml");
    }

    public void showAcquisto() {
        setContent("acquisto-view.fxml");
    }

    public void showOfferta() {
        setContent("offerta.fxml");
    }

    public void showTicket() {
        setContent("ticket-view.fxml");
    }
}
