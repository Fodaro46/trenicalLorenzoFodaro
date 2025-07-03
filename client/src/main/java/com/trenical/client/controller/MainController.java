package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.GetOffertaRequest;
import com.trenical.grpc.OffertaResponse;
import com.trenical.grpc.PromotionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML private StackPane contentPane;
    @FXML private Label promoLabel;
    @FXML private Label userLabel;

    private TrattaController trattaController;

    @FXML
    public void initialize() {
        var user = SessionManager.getInstance().getCurrentUser();
        if (user != null && userLabel != null) {
            userLabel.setText("Benvenuto, " + user.getEmail());
        }
    }

    private boolean checkLogin() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            new Alert(Alert.AlertType.WARNING, "Effettua il login prima di usare questa funzionalità.")
                    .showAndWait();
            return false;
        }
        return true;
    }

    public void showLogin() {
        loadView("login-view.fxml");
    }

    public void showTratte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Trattaview.fxml"));
            Pane pane = loader.load();
            trattaController = loader.getController();
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showOfferta() {
        if (!checkLogin()) return;
        if (!checkTrattaSelection()) return;

        Tratta sel = trattaController.getSelectedTratta();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offerta.fxml"));
            Pane pane = loader.load();
            OffertaController controller = loader.getController();
            if (controller != null) {
                controller.setContesto(buildGrpcTratta(sel));
            }
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        aggiornaBadgePromozione(sel);
    }


    public void showStorico() {
        if (!checkLogin()) return;
        loadView("ticket-history.fxml");
    }

    public void showNotifiche() {
        if (!checkLogin()) return;
        loadView("notifiche.fxml");
    }

    private void loadView(String fxml) {
        try {
            Pane p = FXMLLoader.load(getClass().getResource("/" + fxml));
            contentPane.getChildren().setAll(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkTrattaSelection() {
        if (trattaController == null) {
            new Alert(Alert.AlertType.WARNING, "Visita prima 'Ricerca Tratte'").showAndWait();
            return false;
        }
        Tratta sel = trattaController.getSelectedTratta();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Seleziona una tratta.").showAndWait();
            return false;
        }
        return true;
    }

    private com.trenical.grpc.Tratta buildGrpcTratta(Tratta sel) {
        return com.trenical.grpc.Tratta.newBuilder()
                .setId(sel.getId())
                .setStazionePartenza(sel.getStazionePartenza())
                .setStazioneArrivo(sel.getStazioneArrivo())
                .setOrarioPartenza(sel.getOrarioPartenza())
                .setOrarioArrivo(sel.getOrarioArrivo())
                .setPrezzo(sel.getPrezzo())
                .setData(sel.getData())
                .build();
    }

    private void aggiornaBadgePromozione(Tratta sel) {
        if (promoLabel == null) return;

        promoLabel.setVisible(false);
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                    .usePlaintext()
                    .build();
            var stub = PromotionServiceGrpc.newBlockingStub(channel);
            OffertaResponse resp = stub.getOfferta(
                    GetOffertaRequest.newBuilder()
                            .setUserId(SessionManager.getInstance().getCurrentUser().getUserId())
                            .setTratta(buildGrpcTratta(sel))
                            .build()
            );
            if (!resp.getTipo().equals("Nessuna offerta")) {
                promoLabel.setText("🎁 Promo attiva: " + resp.getTipo());
                promoLabel.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Errore nel badge promozione: " + e.getMessage());
        } finally {
            if (channel != null && !channel.isShutdown()) {
                channel.shutdownNow();
            }
        }
    }
}
