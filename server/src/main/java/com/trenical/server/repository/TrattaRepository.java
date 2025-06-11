package com.trenical.server.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trenical.server.model.Tratta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TrattaRepository {

    private static final String FILE_PATH = "data" + File.separator + "tratte.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Tratta>>() {}.getType();

    public static List<Tratta> caricaTratte() {
        try {
            Path path = Path.of(FILE_PATH);
            if (Files.notExists(path)) {
                // crea cartella e file vuoto se mancano
                Files.createDirectories(path.getParent());
                Files.writeString(path, "[]");
                System.out.println("📄 [DEBUG] Creato file data/tratte.json vuoto.");
            }
            String json = Files.readString(path);
            List<Tratta> list = gson.fromJson(json, LIST_TYPE);
            System.out.println("📥 [LOG] Caricate " + (list != null ? list.size() : 0) + " tratte.");
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("❌ [ERRORE] Lettura data/tratte.json fallita: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvaTratte(List<Tratta> tratte) {
        try {
            Path path = Path.of(FILE_PATH);
            Files.createDirectories(path.getParent());
            try (FileWriter writer = new FileWriter(path.toFile())) {
                gson.toJson(tratte, writer);
            }
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
