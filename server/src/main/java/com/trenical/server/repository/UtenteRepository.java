package com.trenical.server.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trenical.server.model.Utente;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;

public class UtenteRepository {

    private static final Path PATH = Path.of("data", "utenti.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Utente>>() {}.getType();

    public static void salvaUtente(Utente u) {
        try {
            // Assicuro che la cartella esista
            Files.createDirectories(PATH.getParent());
            // Carico lista (o creo file vuoto se non esiste)
            List<Utente> utenti = caricaTutti();
            utenti.removeIf(existing -> existing.getEmail().equalsIgnoreCase(u.getEmail()));
            utenti.add(u);
            // Scrivo su file
            try (FileWriter writer = new FileWriter(PATH.toFile())) {
                GSON.toJson(utenti, writer);
            }
            System.out.println("💾 [LOG] Utenti salvati: " + utenti.size());
        } catch (IOException e) {
            System.err.println("❌ [ERRORE] Salvataggio utenti fallito: " + e.getMessage());
        }
    }

    public static List<Utente> caricaTutti() {
        try {
            if (Files.notExists(PATH)) {
                // Creo file con array vuoto
                Files.createDirectories(PATH.getParent());
                Files.writeString(PATH, "[]");
                System.out.println("📄 [DEBUG] Creato data/utenti.json vuoto.");
            }
            String json = Files.readString(PATH);
            List<Utente> list = GSON.fromJson(json, LIST_TYPE);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("❌ [ERRORE] Lettura utenti fallita: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static Utente caricaPerEmail(String email) {
        return caricaTutti().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
