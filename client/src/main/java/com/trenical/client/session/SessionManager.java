package com.trenical.client.session;

import com.trenical.client.model.User;
import com.trenical.grpc.Notifica;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionManager {

    private static SessionManager instance;
    private User currentUser;
    private final List<Notifica> notificheRicevute;
    private final List<NotificaListener> listeners;

    private SessionManager() {
        this.notificheRicevute = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
        this.notificheRicevute.clear();
        this.listeners.clear();
        System.out.println("[DEBUG] Login effettuato per: " + user.getEmail());
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void logout() {
        this.currentUser = null;
        this.notificheRicevute.clear();
        this.listeners.clear();
        System.out.println("[DEBUG] Logout eseguito, sessione resettata.");
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    public synchronized void aggiungiNotifica(Notifica n) {
        System.out.println("[DEBUG] Tentativo di aggiunta notifica:");
        System.out.println("        Messaggio: " + n.getMessaggio());
        System.out.println("        ID: " + (n.getId().isBlank() ? "VUOTO ❌" : n.getId()));
        System.out.println("        User: " + n.getUserId());

        if (n.getUserId().isBlank() || n.getId().isBlank()) {
            System.err.println("[WARN] Notifica non valida ignorata (userId o id vuoto).");
            return;
        }

        boolean duplicato = notificheRicevute.stream().anyMatch(x -> x.getId().equals(n.getId()));
        if (duplicato) {
            System.out.println("[DEBUG] 🔁 Notifica duplicata ignorata: " + n.getMessaggio());
            return;
        }

        notificheRicevute.add(n);
        System.out.println("[DEBUG]  Notifica aggiunta. Totali ora: " + notificheRicevute.size());
        System.out.println("[DEBUG]  Listener attivi: " + listeners.size());

        // Notifica tutti i listener attivi
        notificaListeners(n);
    }

    public synchronized List<Notifica> getNotifiche() {
        return Collections.unmodifiableList(this.notificheRicevute);
    }

    public synchronized void aggiungiListener(NotificaListener l) {
        for (NotificaListener existing : listeners) {
            if (existing.getClass().equals(l.getClass())) {
                System.out.println("[DEBUG] Listener già registrato, ignorato: " + l.getClass().getSimpleName());
                return;
            }
        }

        listeners.add(l);
        System.out.println("[DEBUG] Listener aggiunto: " + l.getClass().getSimpleName());

        // 🔍 Reinvia solo se il listener è appena aggiunto E non ha ancora ricevuto le notifiche
        // Condizione: se notifiche ricevute non sono già state notificate (non possiamo verificarlo senza uno stato per-listener)
        // → disabilitato per evitare duplicati
        // inviaNotificheAccumulateAListener(l);
    }

    public synchronized void rimuoviListener(NotificaListener l) {
        listeners.remove(l);
        System.out.println("[DEBUG] Listener rimosso: " + l.getClass().getSimpleName());
    }

    public synchronized int getListenerCount() {
        return listeners.size();
    }

    public synchronized List<NotificaListener> getListeners() {
        return Collections.unmodifiableList(this.listeners);
    }

    /**
     *  Invia tutte le notifiche accumulate a un listener appena registrato
     */
    private void inviaNotificheAccumulateAListener(NotificaListener listener) {
        if (notificheRicevute.isEmpty()) {
            System.out.println("[DEBUG] Nessuna notifica accumulata da reinviare");
            return;
        }

        System.out.println("[DEBUG] Reinvio " + notificheRicevute.size() + " notifiche accumulate a: " +
                listener.getClass().getSimpleName());

        // Esegui il reinvio nel thread JavaFX per evitare problemi di concorrenza
        Platform.runLater(() -> {
            for (Notifica n : notificheRicevute) {
                try {
                    System.out.println("[DEBUG] → Reinvio notifica: " + n.getMessaggio());
                    listener.onNuovaNotifica(n);
                } catch (Exception e) {
                    System.err.println("[ERROR] Errore reinvio notifica: " + e.getMessage());
                }
            }
            System.out.println("[DEBUG] ✅ Reinvio completato");
        });
    }

    /**
     * Notifica tutti i listener attivi di una nuova notifica
     */
    private void notificaListeners(Notifica n) {
        for (NotificaListener l : listeners) {
            try {
                System.out.println("[DEBUG] → Notifico listener: " + l.getClass().getSimpleName());
                l.onNuovaNotifica(n);
            } catch (Exception e) {
                System.err.println("[ERROR] Listener errore: " + e.getMessage());
            }
        }
    }

    /**
     *  (solo per debug)
     */
    public void forzaReinvioNotifiche() {
        System.out.println("[DEBUG] 🚨 FORZA REINVIO - Listener attivi: " + listeners.size());
        System.out.println("[DEBUG] 🚨 FORZA REINVIO - Notifiche accumulate: " + notificheRicevute.size());

        for (NotificaListener l : listeners) {
            inviaNotificheAccumulateAListener(l);
        }
    }
}