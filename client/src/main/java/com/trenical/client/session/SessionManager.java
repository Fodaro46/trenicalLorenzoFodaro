package com.trenical.client.session;

import com.trenical.client.model.User;

public class SessionManager {

    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {
        // Costruttore privato per impedire istanze esterne
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }
}
