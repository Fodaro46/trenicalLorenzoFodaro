package com.trenical.server.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trenical.grpc.Notifica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationRegistry {

    private static final String FOLDER_PATH = "server/data";
    private static final String FILE_PATH = FOLDER_PATH + "/notifiche.json";
    private static final Map<String, List<Notifica>> notifichePerUtente = new ConcurrentHashMap<>();
    private static final Gson gson = new Gson();

    static {
        creaCartellaEDati();
        caricaNotifiche();
    }

    private static void creaCartellaEDati() {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created) {
                System.out.println("📁 [NotificationRegistry] Cartella '" + FOLDER_PATH + "' creata.");
            } else {
                System.err.println("⚠️ [NotificationRegistry] Creazione cartella '" + FOLDER_PATH + "' fallita.");
            }
        }
    }

    private static void caricaNotifiche() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("📄 [NotificationRegistry] Creato nuovo file notifiche.json in " + FILE_PATH);
                } else {
                    System.err.println("⚠️ [NotificationRegistry] Creazione notifiche.json fallita.");
                }
                return;
            }

            String json = Files.readString(Path.of(FILE_PATH));
            if (!json.isBlank()) {
                Type type = new TypeToken<Map<String, List<Notifica>>>() {}.getType();
                Map<String, List<Notifica>> caricate = gson.fromJson(json, type);
                if (caricate != null) {
                    notifichePerUtente.putAll(caricate);
                }
            }

            System.out.println("📂 [NotificationRegistry] Notifiche caricate da " + FILE_PATH);

        } catch (IOException e) {
            System.err.println("❌ [NotificationRegistry] Errore lettura notifiche.json: " + e.getMessage());
        }
    }

    public static synchronized void addNotification(String userId, Notifica notificaOriginale) {
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
                System.out.println(" [NotificationRegistry] Notifica segnata come letta: " + idNotifica);
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
        System.out.println("[NotificationRegistry] Tutte le notifiche di " + userId + " segnate come lette");
    }

    private static void salvaNotifiche() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(notifichePerUtente, writer);
        } catch (IOException e) {
            System.err.println(" [NotificationRegistry] Errore salvataggio notifiche.json: " + e.getMessage());
        }
    }
}
