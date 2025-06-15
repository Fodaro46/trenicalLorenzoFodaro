package com.trenical.client.controller;

import com.trenical.client.session.SessionManager;
import com.trenical.grpc.Notifica;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NotificheController {

    @FXML private TextArea notificheArea;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            for (Notifica n : SessionManager.getInstance().getNotifiche()) {
                notificheArea.appendText("[" + n.getTimestamp() + "] " + n.getMessaggio() + "\n");
            }

            Stage stage = (Stage) notificheArea.getScene().getWindow();
            stage.setOnHiding(e -> {
                // Nessuna chiusura canale qui. Stream gestito dal LoginController.
            });
        });
    }

    @FXML
    public void aggiornaNotifiche() {
        notificheArea.clear();
        for (Notifica n : SessionManager.getInstance().getNotifiche()) {
            notificheArea.appendText("[" + n.getTimestamp() + "] " + n.getMessaggio() + "\n");
        }
    }
}
