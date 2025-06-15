package com.trenical.server.util;

import com.trenical.grpc.Notifica;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StreamManager {

    private static final Map<String, StreamObserver<Notifica>> activeStreams = new ConcurrentHashMap<>();

    public static void registra(String userId, StreamObserver<Notifica> observer) {
        activeStreams.put(userId, observer);
        System.out.println("🔄 [STREAM] Registrato stream per " + userId);
    }

    public static void rimuovi(String userId) {
        activeStreams.remove(userId);
        System.out.println("❌ [STREAM] Rimosso stream per " + userId);
    }

    public static StreamObserver<Notifica> get(String userId) {
        return activeStreams.get(userId);
    }
}
