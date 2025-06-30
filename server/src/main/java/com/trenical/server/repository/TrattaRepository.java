package com.trenical.server.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trenical.server.model.Tratta;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TrattaRepository {
    private static final Path PATH = Paths.get(
            System.getProperty("user.dir"), "server", "data", "tratte.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Tratta>>() {}.getType();

    public static List<Tratta> caricaTratte() {
        try {
            if (Files.notExists(PATH)) {
                Files.createDirectories(PATH.getParent());
                Files.writeString(PATH, "[]");
            }
            String json = Files.readString(PATH);
            List<Tratta> list = GSON.fromJson(json, LIST_TYPE);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Errore lettura tratte", e);
        }
    }

    public static synchronized void salvaTratte(List<Tratta> tratte) {
        for (Tratta t : tratte) {
            t.setSchemaVersion(1);
        }
        try {
            String json = GSON.toJson(tratte);
            Path tmp = Files.createTempFile(PATH.getParent(), "tratte", ".json");
            Files.writeString(tmp, json, StandardOpenOption.WRITE);
            Files.move(tmp, PATH,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Errore salvataggio tratte", e);
        }
    }

    public static synchronized void aggiungiTratta(Tratta nuova) {
        List<Tratta> list = caricaTratte();
        list.add(nuova);
        salvaTratte(list);
    }
}
