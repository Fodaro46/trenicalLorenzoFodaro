package com.trenical.server.service;

import com.trenical.server.repository.TrattaRepository;
import common.*;
import io.grpc.stub.StreamObserver;
import model.Tratta;

import java.util.List;

public class TrattaServiceImpl extends TrattaServiceGrpc.TrattaServiceImplBase {

    @Override
    public void cercaTratte(CercaTratteRequest request, StreamObserver<CercaTratteResponse> responseObserver) {
        String partenza = request.getStazionePartenza().toLowerCase();
        String arrivo = request.getStazioneArrivo().toLowerCase();

        System.out.println("📥 [SERVER] Richiesta tratta da '" + partenza + "' a '" + arrivo + "'");

        List<Tratta> tutteLeTratte = TrattaRepository.caricaTratte();
        System.out.println("📦 [SERVER] Numero totale tratte caricate dal file: " + tutteLeTratte.size());
        for (Tratta t : tutteLeTratte) {
            System.out.println("🔸 " + t);
        }

        CercaTratteResponse.Builder responseBuilder = CercaTratteResponse.newBuilder();

        for (Tratta tratta : tutteLeTratte) {
            if (tratta.getStazionePartenza().toLowerCase().contains(partenza)
                    && tratta.getStazioneArrivo().toLowerCase().contains(arrivo)) {

                common.Tratta grpcTratta = common.Tratta.newBuilder()
                        .setId(tratta.getId())
                        .setStazionePartenza(tratta.getStazionePartenza())
                        .setStazioneArrivo(tratta.getStazioneArrivo())
                        .setOrarioPartenza(tratta.getOrarioPartenza())
                        .setOrarioArrivo(tratta.getOrarioArrivo())
                        .setPrezzo(tratta.getPrezzo())
                        .build();

                responseBuilder.addTratte(grpcTratta);
            }
        }

        int trovate = responseBuilder.getTratteCount();
        System.out.println("✅ [SERVER] Tratte corrispondenti trovate: " + trovate);

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void acquistaBiglietto(AcquistaBigliettoRequest request, StreamObserver<AcquistaBigliettoResponse> responseObserver) {
        String idTratta = request.getIdTratta();
        System.out.println("🎟️ [SERVER] Richiesta acquisto biglietto per ID tratta: " + idTratta);

        List<Tratta> tutteLeTratte = TrattaRepository.caricaTratte();
        boolean esiste = tutteLeTratte.stream().anyMatch(t -> t.getId().equals(idTratta));

        System.out.println("🔍 [SERVER] Tratta trovata: " + esiste);

        AcquistaBigliettoResponse response = AcquistaBigliettoResponse.newBuilder()
                .setSuccesso(esiste)
                .setMessaggio(esiste ? "Biglietto acquistato con successo!" : "Tratta non trovata.")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
