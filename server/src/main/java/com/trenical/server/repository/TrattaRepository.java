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

    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "server", "data", "tratte.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Tratta>>() {}.getType();

    public static List<Tratta> caricaTratte() {
        try {
            if (Files.notExists(PATH)) {
                Files.createDirectories(PATH.getParent());
                Files.writeString(PATH, "[]");
                System.out.println("📄 [DEBUG] Creato data/tratte.json vuoto.");
            }
            String json = Files.readString(PATH);
            List<Tratta> list = GSON.fromJson(json, LIST_TYPE);
            System.out.println("📥 [LOG] Caricate " + (list != null ? list.size() : 0) + " tratte.");
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("❌ [ERRORE] Lettura data/tratte.json fallita: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static synchronized void salvaTratte(List<Tratta> tratte) {
        try {
            String json = GSON.toJson(tratte);
            Path temp = Files.createTempFile(PATH.getParent(), "tratte", ".json");
            Files.writeString(temp, json, StandardOpenOption.WRITE);
            Files.move(temp, PATH, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("💾 [LOG] Salvate " + tratte.size() + " tratte in data/tratte.json.");
        } catch (IOException e) {
            System.err.println("❌ [ERRORE] Salvataggio data/tratte.json fallito: " + e.getMessage());
        }
    }

    public static void aggiungiTratta(Tratta nuova) {
        List<Tratta> tratte = caricaTratte();
        tratte.add(nuova);
        salvaTratte(tratte);
    }
}
