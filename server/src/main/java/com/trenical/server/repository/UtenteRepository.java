package com.trenical.server.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trenical.server.model.Utente;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteRepository {
    private static final Path PATH = Paths.get(
            System.getProperty("user.dir"), "server", "data", "utenti.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Utente>>() {}.getType();

    public static List<Utente> caricaTutti() {
        try {
            if (Files.notExists(PATH)) {
                Files.createDirectories(PATH.getParent());
                Files.writeString(PATH, "[]");
            }
            String json = Files.readString(PATH);
            List<Utente> list = GSON.fromJson(json, LIST_TYPE);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Errore lettura utenti", e);
        }
    }
    public static synchronized Utente caricaPerId(String userId) {
        for (Utente u : caricaTutti()) {
            if (userId.equals(u.getUserId())) return u;
        }
        return null;
    }

    public static synchronized void salvaUtente(Utente u) {
        u.setSchemaVersion(1);
        List<Utente> list = caricaTutti();
        list.removeIf(existing -> existing.getEmail().equalsIgnoreCase(u.getEmail()));
        list.add(u);

        try {
            String json = GSON.toJson(list);
            Path tmp = Files.createTempFile(PATH.getParent(), "utenti", ".json");
            Files.writeString(tmp, json, StandardOpenOption.WRITE);
            Files.move(tmp, PATH,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Errore salvataggio utenti", e);
        }
    }

    public static synchronized Utente caricaPerEmail(String email) {
        for (Utente u : caricaTutti()) {
            if (email.equalsIgnoreCase(u.getEmail())) return u;
        }
        return null;
    }
}
