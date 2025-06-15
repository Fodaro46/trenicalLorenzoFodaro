package com.trenical.server.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trenical.server.model.Biglietto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class BigliettoRepository {

    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "server", "data", "biglietti.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Biglietto>>() {}.getType();

    public static List<Biglietto> caricaBiglietti() {
        try {
            if (Files.notExists(PATH)) {
                Files.createDirectories(PATH.getParent());
                Files.writeString(PATH, "[]");
                System.out.println("📄 [DEBUG] Creato data/biglietti.json vuoto.");
            }
            String json = Files.readString(PATH);
            List<Biglietto> list = GSON.fromJson(json, LIST_TYPE);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("❌ [ERRORE] Lettura biglietti fallita: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static synchronized void salvaBiglietto(Biglietto biglietto) {
        // Validazione userId
        if (biglietto.getUserId() == null || biglietto.getUserId().isEmpty()) {
            throw new IllegalArgumentException("UserId mancante per il biglietto: " + biglietto.getId());
        }
        List<Biglietto> lista = caricaBiglietti();
        lista.add(biglietto);

        try {
            String json = GSON.toJson(lista);
            // Scrittura atomica su file temporaneo
            Path temp = Files.createTempFile(PATH.getParent(), "biglietti", ".json");
            Files.writeString(temp, json, StandardOpenOption.WRITE);
            Files.move(temp, PATH, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("💾 [LOG] Biglietto salvato: " + biglietto.getId());
        } catch (IOException e) {
            System.err.println("❌ [ERRORE] Salvataggio biglietto fallito: " + e.getMessage());
        }
    }

    public static List<Biglietto> getByUserId(String userId) {
        List<Biglietto> biglietti = caricaBiglietti().stream()
                .filter(b -> b.getUserId().equals(userId))
                .toList();
        System.out.println("🔎 [LOG] Biglietti per " + userId + ": " + biglietti.size());
        return biglietti;
    }
}
