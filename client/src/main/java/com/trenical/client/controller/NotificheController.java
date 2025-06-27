package com.trenical.client.controller;

import com.trenical.client.session.NotificaListener;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.Notifica;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class NotificheController implements NotificaListener {

    @FXML private TextArea notificheArea;

    @FXML
    public void initialize() {
        SessionManager.getInstance().aggiungiListener(this);
        aggiornaNotifiche();  // Mostra le notifiche già ricevute al primo avvio
    }

    @FXML
    public void aggiornaNotifiche() {
        Platform.runLater(() -> {
            var lista = SessionManager.getInstance().getNotifiche();
            notificheArea.clear();
            for (Notifica n : lista) {
                notificheArea.appendText(format(n) + "\n\n");
            }
        });
    }

    @Override
    public void onNuovaNotifica(Notifica n) {
        Platform.runLater(() -> notificheArea.appendText(format(n) + "\n\n"));
    }

    private String format(Notifica n) {
        return "🕒 " + n.getTimestamp() +
                "\n🧾 " + n.getMessaggio();
    }
}
