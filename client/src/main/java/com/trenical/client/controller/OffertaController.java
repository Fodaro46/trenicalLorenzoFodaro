package com.trenical.client.controller;

import com.trenical.grpc.GetOffertaRequest;
import com.trenical.grpc.OffertaResponse;
import com.trenical.grpc.PromotionServiceGrpc;
import com.trenical.grpc.Tratta;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OffertaController {

    @FXML private Label tipoLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label descrizioneLabel;

    private ManagedChannel channel;
    private PromotionServiceGrpc.PromotionServiceBlockingStub stub;

    private com.trenical.client.model.Tratta trattaSelezionata;
    private String userId;

    @FXML
    public void initialize() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        this.stub = PromotionServiceGrpc.newBlockingStub(channel);
    }

    public void setContesto(com.trenical.client.model.Tratta tratta, String userId) {
        this.trattaSelezionata = tratta;
        this.userId = userId;
        caricaOfferta();
    }

    private void caricaOfferta() {
        try {
            Tratta grpcTratta = Tratta.newBuilder()
                    .setId(trattaSelezionata.getId())
                    .setStazionePartenza(trattaSelezionata.getStazionePartenza())
                    .setStazioneArrivo(trattaSelezionata.getStazioneArrivo())
                    .setOrarioPartenza(trattaSelezionata.getOrarioPartenza())
                    .setOrarioArrivo(trattaSelezionata.getOrarioArrivo())
                    .setPrezzo(trattaSelezionata.getPrezzo())
                    .build();

            GetOffertaRequest request = GetOffertaRequest.newBuilder()
                    .setTratta(grpcTratta)
                    .setUserId(userId)
                    .build();

            OffertaResponse response = stub.getOfferta(request);

            tipoLabel.setText("Tipo: " + response.getTipo());
            prezzoLabel.setText("Prezzo Scontato: €" + String.format("%.2f", response.getPrezzoScontato()));
            descrizioneLabel.setText("Descrizione: " + response.getDescrizione());

        } catch (Exception e) {
            tipoLabel.setText("Errore");
            prezzoLabel.setText("");
            descrizioneLabel.setText(e.getMessage());
        }
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
