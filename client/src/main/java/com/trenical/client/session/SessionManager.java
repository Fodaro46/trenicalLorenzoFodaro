package com.trenical.client.session;

import com.trenical.client.model.User;
import com.trenical.grpc.Notifica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionManager {

    private static SessionManager instance;
    private User currentUser;
    private final List<Notifica> notificheRicevute;

    private SessionManager() {
        this.notificheRicevute = new ArrayList<>();
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
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void logout() {
        this.currentUser = null;
        this.notificheRicevute.clear();
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    public void aggiungiNotifica(Notifica n) {
        this.notificheRicevute.add(n);
    }

    public List<Notifica> getNotifiche() {
        return Collections.unmodifiableList(this.notificheRicevute);
    }
}
