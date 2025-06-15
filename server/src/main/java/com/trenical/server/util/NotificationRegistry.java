package com.trenical.server.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.trenical.grpc.Notifica;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public class NotificationRegistry {

    private static final Path FILE = Paths.get("server", "data", "notifiche.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type TYPE = new TypeToken<Map<String, List<Notifica>>>() {}.getType();
    private static final Map<String, List<Notifica>> notifiche = new HashMap<>();

    static {
        caricaDaFile();
    }

    public static synchronized boolean addNotification(String userId, Notifica n) {
        Notifica withId = n.toBuilder().setId(UUID.randomUUID().toString()).build();
        notifiche.computeIfAbsent(userId, k -> new ArrayList<>()).add(withId);
        salvaSuFile();
        System.out.println("📌 [LOG] Nuova notifica per " + userId + ": " + withId.getMessaggio());

        boolean live = StreamManager.invia(userId, withId);

        if (!live) {
            System.out.println("⚠️ [DEBUG] Nessuno stream attivo per " + userId + " — ritento tra 1 secondo...");
            // Retry asincrono singolo dopo 1 secondo
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    boolean retryLive = StreamManager.invia(userId, withId);
                    System.out.println("📌 [RETRY] Notifica " + (retryLive ? "inviata live" : "ancora accumulata")
                            + " a " + userId + ": " + withId.getMessaggio());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            System.out.println("📌 [LOG] Notifica inviata live a " + userId + ": " + withId.getMessaggio());
        }

        return live;
    }

    public static synchronized List<Notifica> getUnreadNotifications(String userId) {
        List<Notifica> raw = notifiche.getOrDefault(userId, List.of());
        List<Notifica> withIds = new ArrayList<>();
        for (Notifica n : raw) {
            if (n.getId().isBlank()) {
                System.out.println("⚠️ [FIX] Assegnato nuovo ID a notifica pendente per " + userId);
                n = n.toBuilder().setId(UUID.randomUUID().toString()).build();
            }
            withIds.add(n);
        }
        return withIds;
    }

    public static synchronized void markAllAsRead(String userId) {
        notifiche.remove(userId);
        salvaSuFile();
        System.out.println("✅ [LOG] Tutte le notifiche lette per: " + userId);
    }

    private static void caricaDaFile() {
        try {
            if (Files.notExists(FILE)) {
                Files.createDirectories(FILE.getParent());
                Files.writeString(FILE, "{}");
            }
            String json = Files.readString(FILE);
            Map<String, List<Notifica>> data = GSON.fromJson(json, TYPE);
            if (data != null) notifiche.putAll(data);
        } catch (IOException e) {
            System.err.println("❌ Lettura notifiche fallita: " + e.getMessage());
        }
    }

    private static void salvaSuFile() {
        try {
            String json = GSON.toJson(notifiche, TYPE);
            Path temp = Files.createTempFile(FILE.getParent(), "notifiche", ".json");
            Files.writeString(temp, json);
            Files.move(temp, FILE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("❌ Salvataggio notifiche fallito: " + e.getMessage());
        }
    }
}
