package com.trenical.client.controller;

import com.trenical.client.session.NotificaListener;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.Notifica;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.HashSet;
import java.util.Set;

public class NotificheController implements NotificaListener {

    @FXML
    private TextArea notificheArea;

    // Per evitare duplicazioni durante il reinvio
    private final Set<String> notificheGiaMostrate = new HashSet<>();

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] 🚀 NotificheController initialize() chiamato");

        // Pulisci l'area delle notifiche
        notificheArea.clear();
        notificheArea.setText("📡 Connessione alle notifiche in corso...\n");

        // 🔥 REGISTRA IL LISTENER - questo scatenerà automaticamente il reinvio
        SessionManager.getInstance().aggiungiListener(this);

        System.out.println("[DEBUG] ✅ Listener registrato, SessionManager invierà automaticamente le notifiche accumulate");
    }

    @FXML
    public void aggiornaNotifiche() {
        System.out.println("[DEBUG] 🔄 Aggiornamento manuale notifiche richiesto");

        notificheArea.clear();
        notificheGiaMostrate.clear();

        var lista = SessionManager.getInstance().getNotifiche();
        if (lista.isEmpty()) {
            notificheArea.setText("📭 Nessuna notifica disponibile.");
        } else {
            notificheArea.setText("📬 Notifiche caricate (" + lista.size() + "):\n\n");
            for (Notifica n : lista) {
                String formatted = format(n);
                notificheArea.appendText(formatted);
                notificheGiaMostrate.add(n.getId());
            }
        }

        System.out.println("[DEBUG] ✅ Aggiornamento manuale completato: " + lista.size() + " notifiche");
    }

    @FXML
    public void segnaTutteComeLette() {
        notificheArea.clear();
        notificheGiaMostrate.clear();
        notificheArea.setText("✅ Tutte le notifiche sono state segnate come lette.");
        System.out.println("[DEBUG] 🏷️ Notifiche segnate come lette (solo lato GUI)");
    }

    private String format(Notifica n) {
        return "🔔 [" + n.getTimestamp() + "] " + n.getMessaggio() + "\n";
    }

    @Override
    public void onNuovaNotifica(Notifica n) {
        // Questo metodo viene chiamato sia per le notifiche accumulate che per quelle nuove
        Platform.runLater(() -> {
            System.out.println("[DEBUG] 📨 onNuovaNotifica chiamato per: " + n.getMessaggio());

            // Evita duplicazioni
            if (notificheGiaMostrate.contains(n.getId())) {
                System.out.println("[DEBUG] 🔁 Notifica già mostrata, ignorata: " + n.getId());
                return;
            }

            // Se l'area è vuota o contiene solo il messaggio di caricamento, puliscila
            String currentText = notificheArea.getText();
            if (currentText.contains("📡 Connessione alle notifiche") ||
                    currentText.contains("📭 Nessuna notifica disponibile")) {
                notificheArea.clear();
                notificheArea.setText("📬 Notifiche:\n\n");
            }

            // Aggiungi la nuova notifica
            String formatted = format(n);
            notificheArea.appendText(formatted);
            notificheGiaMostrate.add(n.getId());

            // Scorri automaticamente verso il basso per vedere l'ultima notifica
            notificheArea.setScrollTop(Double.MAX_VALUE);

            System.out.println("[DEBUG] ✅ Notifica aggiunta alla GUI: " + n.getMessaggio());
        });
    }

    /**
     * Cleanup quando il controller viene distrutto
     */
    public void cleanup() {
        SessionManager.getInstance().rimuoviListener(this);
        System.out.println("[DEBUG] 🧹 NotificheController cleanup eseguito");
    }
}