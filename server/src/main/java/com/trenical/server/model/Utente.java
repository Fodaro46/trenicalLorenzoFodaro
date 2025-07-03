package com.trenical.server.model;

public class Utente {
    private int schemaVersion = 1;
    private String userId;
    private String email;
    private boolean isFedelta;

    public Utente() {}

    public Utente(String userId, String email) {
        this.schemaVersion = 1;
        this.userId = userId;
        this.email = email;
        this.isFedelta = false;
    }

    public int getSchemaVersion() {
        return schemaVersion;
    }
    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFedelta() {
        return isFedelta;
    }
    public void setFedelta(boolean fedelta) {
        isFedelta = fedelta;
    }
    public boolean isStudente() {
        return email != null && email.toLowerCase().endsWith("@studenti.unical.it");
    }
}
