package com.trenical.client.controller;

import com.trenical.client.model.Tratta;
import com.trenical.client.session.SessionManager;
import com.trenical.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;

public class AcquistoController {

    @FXML private Label trattaLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label orarioLabel;
    @FXML private DatePicker dataPicker;

    private Tratta tratta;
    private double prezzoFinale;
    private ManagedChannel channel;

    @FXML
    public void initialize() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        dataPicker.setValue(LocalDate.now());
        dataPicker.setDayCellFactory(getDayCellFactory());
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now()));
            }
        };
    }

    public void setTratta(Tratta t) {
        this.tratta = t;
        trattaLabel.setText(t.getStazionePartenza() + " → " + t.getStazioneArrivo());
        orarioLabel.setText(t.getOrarioPartenza());  // mostra ma non modifica
        calcolaPrezzoScontato(t);
    }

    private void calcolaPrezzoScontato(Tratta t) {
        try {
            var stub = PromotionServiceGrpc.newBlockingStub(channel);
            String data = dataPicker.getValue() != null
                    ? dataPicker.getValue().toString()
                    : LocalDate.now().toString();

            OffertaResponse offerta = stub.getOfferta(
                    GetOffertaRequest.newBuilder()
                            .setUserId(SessionManager.getInstance().getCurrentUser().getUserId())
                            .setTratta(com.trenical.grpc.Tratta.newBuilder()
                                    .setId(t.getId())
                                    .setStazionePartenza(t.getStazionePartenza())
                                    .setStazioneArrivo(t.getStazioneArrivo())
                                    .setOrarioPartenza(t.getOrarioPartenza())
                                    .setOrarioArrivo(t.getOrarioArrivo())
                                    .setPrezzo(t.getPrezzo())
                                    .setData(data)
                                    .build())
                            .build()
            );

            prezzoFinale = offerta.getPrezzoScontato();
            prezzoLabel.setText("€ " + String.format("%.2f", prezzoFinale));
        } catch (Exception e) {
            prezzoFinale = t.getPrezzo();
            prezzoLabel.setText("€ " + String.format("%.2f", prezzoFinale));
        }
    }

    @FXML
    private void onConfirmAcquisto() {
        if (dataPicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Seleziona la data del viaggio.").showAndWait();
            return;
        }

        String data = dataPicker.getValue().toString();
        String orario = tratta.getOrarioPartenza();  // orario FISSO

        try {
            var stub = TrenicalServiceGrpc.newBlockingStub(channel);
            stub.acquistaBiglietto(
                    BigliettoRequest.newBuilder()
                            .setUserId(SessionManager.getInstance().getCurrentUser().getUserId())
                            .setTratta(tratta.getId())
                            .setData(data)
                            .setOrario(orario)
                            .build()
            );

            new Alert(Alert.AlertType.INFORMATION,
                    "Acquisto confermato!\nPrezzo finale: € " + String.format("%.2f", prezzoFinale))
                    .showAndWait();

            if (channel != null && !channel.isShutdown()) {
                channel.shutdownNow();
            }

            // Chiude solo la finestra attuale
            Stage currentStage = (Stage) dataPicker.getScene().getWindow();
            currentStage.close();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Errore durante l'acquisto: " + ex.getMessage()).showAndWait();
        }
    }
}
