package com.trenical.server.util;

import com.trenical.grpc.Notifica;
import io.grpc.stub.StreamObserver;

public class NotificheManager {

    public static void inviaNotifica(String userId, String messaggio) {
        Notifica n = Notifica.newBuilder()
                .setUserId(userId)
                .setMessaggio(messaggio)
                .setTimestamp(java.time.LocalDateTime.now().toString())
                .build();

        // Salvataggio nel registro (opzionale, per storico)
        NotificationRegistry.addNotification(userId, n);

        // Invio se il client è connesso
        StreamObserver<Notifica> stream = StreamManager.get(userId);
        if (stream != null) {
            try {
                stream.onNext(n);
                System.out.println("📤 [NOTIFICA] Inviata a " + userId);
            } catch (Exception e) {
                System.err.println("⚠️ [NOTIFICA] Fallita per " + userId + ": " + e.getMessage());
                StreamManager.rimuovi(userId); // cleanup se lo stream è rotto
            }
        } else {
            System.out.println("🕓 [NOTIFICA] Nessuno stream attivo per " + userId);
        }
    }
}
