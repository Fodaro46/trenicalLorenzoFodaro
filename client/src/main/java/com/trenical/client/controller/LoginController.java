package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.model.User;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private Label resultLabel;

    private ManagedChannel channel;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    public void onLogin() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            resultLabel.setText("Inserisci un'email valida.");
            return;
        }

        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TrenicalServiceGrpc.TrenicalServiceBlockingStub stub = TrenicalServiceGrpc.newBlockingStub(channel);
        TrenicalServiceGrpc.TrenicalServiceStub asyncStub = TrenicalServiceGrpc.newStub(channel);

        try {
            //gRPC
            LoginRequest request = LoginRequest.newBuilder()
                    .setEmail(email)
                    .build();

            LoginResponse response = stub.login(request);

            // Salva utente nella sessione
            SessionManager.getInstance().login(new User(response.getUserId(), email));

            resultLabel.setText("Utente: " + response.getUserId() + "\n" + response.getMessage());

            // Stream notifiche
            NotificheRequest notifRequest = NotificheRequest.newBuilder()
                    .setUserId(response.getUserId())
                    .build();

            asyncStub.streamNotifiche(notifRequest, new StreamObserver<Notifica>() {
                @Override
                public void onNext(Notifica notifica) {
                    Platform.runLater(() ->
                            resultLabel.setText("📩 " + notifica.getMessaggio() + " @ " + notifica.getTimestamp())
                    );
                }

                @Override
                public void onError(Throwable t) {
                    LOGGER.log(Level.SEVERE, "Errore stream notifiche", t);
                }

                @Override
                public void onCompleted() {
                    LOGGER.info("✅ Stream notifiche completato.");
                }
            });

            // Mostra la finestra offerta
            mostraOffertaDemo();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il login", e);
            Platform.runLater(() -> resultLabel.setText("Errore durante il login: " + e.getMessage()));
        }
    }

    private void mostraOffertaDemo() {
        try {
            // Tratta simulata, placeholder per ora
            Tratta trattaDemo = new Tratta("T01", "Milano", "Roma", "15:30", "18:45", 89.99);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offerta.fxml"));
            Scene scene = new Scene(loader.load());

            OffertaController controller = loader.getController();
            controller.setContesto(trattaDemo);  // Passa la tratta

            Stage stage = new Stage();
            stage.setTitle("Offerta Attiva");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore nel caricamento della schermata offerta", e);
        }
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
