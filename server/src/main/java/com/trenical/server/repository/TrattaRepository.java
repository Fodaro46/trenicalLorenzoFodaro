package com.trenical.server.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Tratta;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TrattaRepository {
    private static final String FILE_PATH = "tratte.json";
    private static final Gson gson = new Gson();
    private static final Type LIST_TYPE = new TypeToken<List<Tratta>>() {}.getType();

    public static List<Tratta> caricaTratte() {
        try (InputStream is = TrattaRepository.class.getClassLoader().getResourceAsStream(FILE_PATH)) {
            if (is == null) {
                System.out.println("❌ [DEBUG] tratte.json non trovato nel classpath!");
                return new ArrayList<>();
            }
            System.out.println("✅ [DEBUG] tratte.json trovato nel classpath.");
            return gson.fromJson(new InputStreamReader(is), LIST_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void salvaTratte(List<Tratta> tratte) {
        try (FileWriter writer = new FileWriter("src/main/resources/" + FILE_PATH)) {
            gson.toJson(tratte, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void aggiungiTratta(Tratta nuova) {
        List<Tratta> tratte = caricaTratte();
        tratte.add(nuova);
        salvaTratte(tratte);
    }
}
