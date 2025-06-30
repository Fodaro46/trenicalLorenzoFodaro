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
    private static final Path PATH = Paths.get(
            System.getProperty("user.dir"), "server", "data", "biglietti.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Biglietto>>() {}.getType();

    public static List<Biglietto> caricaBiglietti() {
        try {
            if (Files.notExists(PATH)) {
                Files.createDirectories(PATH.getParent());
                Files.writeString(PATH, "[]");
            }
            String json = Files.readString(PATH);
            List<Biglietto> list = GSON.fromJson(json, LIST_TYPE);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Errore lettura biglietti", e);
        }
    }

    public static synchronized void salvaBiglietto(Biglietto b) {
        if (b.getUserId() == null || b.getUserId().isEmpty())
            throw new IllegalArgumentException("UserId mancante");

        List<Biglietto> lista = caricaBiglietti();
        b.setSchemaVersion(1);
        lista.add(b);

        try {
            String json = GSON.toJson(lista);
            Path tmp = Files.createTempFile(PATH.getParent(), "biglietti", ".json");
            Files.writeString(tmp, json, StandardOpenOption.WRITE);
            Files.move(tmp, PATH,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Errore salvataggio biglietti", e);
        }
    }

    public static synchronized List<Biglietto> getByUserId(String userId) {
        List<Biglietto> result = new ArrayList<>();
        for (Biglietto b : caricaBiglietti()) {
            if (userId.equals(b.getUserId())) result.add(b);
        }
        return result;
    }
}
