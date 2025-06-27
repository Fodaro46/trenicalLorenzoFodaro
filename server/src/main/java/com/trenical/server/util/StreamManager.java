package com.trenical.server.util;

import com.trenical.grpc.Notifica;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StreamManager {

    // userId → observer connesso
    private static final Map<String, StreamObserver<Notifica>> observers = new ConcurrentHashMap<>();

    public static void registra(String userId, StreamObserver<Notifica> observer) {
        observers.put(userId, observer);
        System.out.println("✅ [StreamManager] Registrato stream per userId: " + userId);
    }

    public static void disconnetti(String userId) {
        observers.remove(userId);
        System.out.println("❌ [StreamManager] Disconnesso stream per userId: " + userId);
    }

    public static void invia(String userId, Notifica notifica) {
        System.out.println("➡️ [StreamManager] Invio tentativo a “" + userId +
                "”, osservatori registrati: " + observers.keySet());
        StreamObserver<Notifica> observer = observers.get(userId);
        if (observer != null) {
            try {
                observer.onNext(notifica);
                System.out.println("📨 [StreamManager] Inviata notifica live a " + userId + ": " + notifica.getMessaggio());
            } catch (Exception e) {
                System.err.println("⚠️ [StreamManager] Errore invio a " + userId + ": " + e.getMessage());
                disconnetti(userId);
            }
        } else {
            System.out.println("🕓 [StreamManager] Nessun client connesso per " + userId + ", notifica in coda.");
        }
    }

    public static boolean isConnesso(String userId) {
        return observers.containsKey(userId);
    }
}
