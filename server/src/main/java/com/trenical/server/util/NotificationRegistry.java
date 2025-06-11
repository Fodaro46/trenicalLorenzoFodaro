package com.trenical.server.util;

import com.trenical.grpc.Notifica;

import java.util.*;

public class NotificationRegistry {

    private static final Map<String, List<Notifica>> notifiche = new HashMap<>();

    public static synchronized void addNotification(String userId, Notifica n) {
        notifiche.computeIfAbsent(userId, k -> new ArrayList<>()).add(n);
        System.out.println("📌 [LOG] Nuova notifica per " + userId + ": " + n.getMessaggio());
    }

    public static synchronized List<Notifica> getNotifications(String userId) {
        return new ArrayList<>(notifiche.getOrDefault(userId, Collections.emptyList()));
    }
}
