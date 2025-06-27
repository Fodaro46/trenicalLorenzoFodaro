package com.trenical.server.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trenical.grpc.Notifica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationRegistry {

    private static final Map<String, List<Notifica>> notifichePerUtente = new ConcurrentHashMap<>();
    private static final String FILE_PATH = "notifiche.json";
    private static final Gson gson = new Gson();

    static {
        caricaNotifiche();
    }

    private static void caricaNotifiche() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("📄 [NotificationRegistry] Creato nuovo file notifiche.json.");
                } else {
                    System.err.println("⚠️ [NotificationRegistry] Creazione di notifiche.json fallita (file esistente o permessi mancanti).");
                }
                return;
            }

            String json = Files.readString(file.toPath());
            if (!json.isBlank()) {
                Type type = new TypeToken<Map<String, List<Notifica>>>() {}.getType();
                Map<String, List<Notifica>> caricate = gson.fromJson(json, type);
                if (caricate != null) {
                    notifichePerUtente.putAll(caricate);
                }
            }
            System.out.println("📂 [NotificationRegistry] Notifiche caricate da disco.");
        } catch (IOException e) {
            System.err.println("❌ [NotificationRegistry] Errore durante creazione/lettura di notifiche.json: " + e.getMessage());
        }
    }

    public static synchronized void addNotification(String userId, Notifica notificaOriginale) {
        // Genera ID univoco se mancante
        String idGenerato = notificaOriginale.getId().isBlank()
                ? "N-" + UUID.randomUUID()
                : notificaOriginale.getId();

        Notifica notifica = Notifica.newBuilder(notificaOriginale)
                .setId(idGenerato)
                .build();

        notifichePerUtente
                .computeIfAbsent(userId, k -> new ArrayList<>())
                .add(notifica);

        salvaNotifiche();

        System.out.println("📥 [NotificationRegistry] Nuova notifica per " + userId + ": " + notifica.getMessaggio());

        // Se il client è connesso, invia subito
        if (StreamManager.isConnesso(userId)) {
            StreamManager.invia(userId, notifica);
            markAsRead(userId, notifica.getId());
        }
    }

    public static synchronized List<Notifica> getUnreadNotifications(String userId) {
        return notifichePerUtente.getOrDefault(userId, Collections.emptyList()).stream()
                .filter(n -> !n.getId().endsWith("_LETTA"))
                .toList();
    }

    public static synchronized void markAsRead(String userId, String idNotifica) {
        List<Notifica> lista = notifichePerUtente.get(userId);
        if (lista == null) return;

        for (int i = 0; i < lista.size(); i++) {
            Notifica n = lista.get(i);
            if (n.getId().equals(idNotifica)) {
                Notifica letta = Notifica.newBuilder(n)
                        .setId(n.getId() + "_LETTA")
                        .build();
                lista.set(i, letta);
                System.out.println("✅ [NotificationRegistry] Notifica segnata come letta: " + idNotifica);
                break;
            }
        }
        salvaNotifiche();
    }

    public static synchronized void markAllAsRead(String userId) {
        List<Notifica> lista = notifichePerUtente.get(userId);
        if (lista == null) return;

        for (int i = 0; i < lista.size(); i++) {
            Notifica n = lista.get(i);
            if (!n.getId().endsWith("_LETTA")) {
                lista.set(i, Notifica.newBuilder(n)
                        .setId(n.getId() + "_LETTA")
                        .build());
            }
        }
        salvaNotifiche();
        System.out.println("✅ [NotificationRegistry] Tutte le notifiche di " + userId + " segnate come lette");
    }

    private static void salvaNotifiche() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(notifichePerUtente, writer);
        } catch (IOException e) {
            System.err.println("❌ [NotificationRegistry] Errore salvataggio notifiche.json: " + e.getMessage());
        }
    }
}
