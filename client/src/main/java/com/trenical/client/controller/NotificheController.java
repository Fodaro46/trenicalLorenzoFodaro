package com.trenical.client.controller;

import com.trenical.client.session.SessionManager;
import com.trenical.grpc.Notifica;
import com.trenical.grpc.NotificheRequest;
import com.trenical.grpc.TrenicalServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class NotificheController {

    private ManagedChannel channel;

    @FXML
    public void initialize() {
        String userId = SessionManager.getInstance().getCurrentUser().getUserId();
        System.out.println("📡 Avvio stream notifiche per userId: " + userId);

        // 1) Apri il canale gRPC
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        // 2) Crea lo stub e invia la richiesta di streaming
        TrenicalServiceGrpc.TrenicalServiceStub stub = TrenicalServiceGrpc.newStub(channel);
        NotificheRequest request = NotificheRequest.newBuilder()
                .setUserId(userId)
                .build();

        stub.streamNotifiche(request, new StreamObserver<>() {
            @Override
            public void onNext(Notifica notifica) {
                // Mostra ogni notifica come pop-up
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Nuova Notifica");
                    alert.setHeaderText(null);
                    alert.setContentText("🔔 " + notifica.getTimestamp() + " — " + notifica.getMessaggio());
                    alert.show();
                });
            }

            @Override
            public void onError(Throwable t) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore Notifiche");
                    alert.setHeaderText(null);
                    alert.setContentText("Stream notifiche interrotto: " + t.getMessage());
                    alert.showAndWait();
                });
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                // Lo stream rimane aperto per future notifiche
            }
        });

        // 3) Chiudi il canale quando la finestra viene nascosta
        Platform.runLater(() -> {
            Scene scene = getAnyNode().getScene();
            if (scene != null) {
                Window window = scene.getWindow();
                window.setOnHidden(e -> shutdown());
            }
        });
    }

    /**
     * Workaround per ottenere un nodo qualsiasi del controller (non visibile),
     * puoi usare contentPane.getScene() o simile se preferisci.
     */
    private javafx.scene.Node getAnyNode() {
        // Crea un nodo invisibile solo per agganciare la scena
        return new javafx.scene.control.Label();
    }

    /** Chiude il canale gRPC */
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdownNow();
            System.out.println("🛑 Channel notifiche shutdown");
        }
    }
}
