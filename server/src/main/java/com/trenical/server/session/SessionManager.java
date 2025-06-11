package com.trenical.server.session;

import com.trenical.server.model.Utente;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static SessionManager instance;
    private final Map<String, Utente> utentiLoggati;

    private SessionManager() {
        utentiLoggati = new ConcurrentHashMap<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String login(String email) {
        String userId = java.util.UUID.randomUUID().toString();

        utentiLoggati.put(userId, new Utente(userId, email));

        return userId;
    }

    public Utente getUtente(String userId) {
        return utentiLoggati.get(userId);
    }

    public boolean isLogged(String userId) {
        return utentiLoggati.containsKey(userId);
    }

    public void logout(String userId) {
        utentiLoggati.remove(userId);
    }

    public Map<String, Utente> getUtentiLoggati() {
        return utentiLoggati;
    }
}
