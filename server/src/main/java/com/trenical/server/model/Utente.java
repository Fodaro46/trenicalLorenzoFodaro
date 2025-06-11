package com.trenical.server.model;

public class Utente {
    private String userId;
    private String email;

    public Utente() {
        // Costruttore vuoto per Gson
    }

    public Utente(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
