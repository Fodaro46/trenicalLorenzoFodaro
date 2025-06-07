package com.trenical.server.service;

import common.AcquistaBigliettoRequest;
import common.AcquistaBigliettoResponse;
import common.CercaTratteRequest;
import common.CercaTratteResponse;
import common.Tratta;
import common.TrattaServiceGrpc;

import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TrattaServiceImpl extends TrattaServiceGrpc.TrattaServiceImplBase {

    private final List<Tratta> tratteDisponibili = Arrays.asList(
            Tratta.newBuilder()
                    .setId(UUID.randomUUID().toString())
                    .setStazionePartenza("Milano")
                    .setStazioneArrivo("Roma")
                    .setOrarioPartenza("08:00")
                    .setOrarioArrivo("11:30")
                    .setPrezzo(49.99)
                    .build(),
            Tratta.newBuilder()
                    .setId(UUID.randomUUID().toString())
                    .setStazionePartenza("Firenze")
                    .setStazioneArrivo("Napoli")
                    .setOrarioPartenza("10:00")
                    .setOrarioArrivo("13:45")
                    .setPrezzo(39.50)
                    .build()
    );

    @Override
    public void cercaTratte(CercaTratteRequest request, StreamObserver<CercaTratteResponse> responseObserver) {
        String partenza = request.getStazionePartenza().toLowerCase();
        String arrivo = request.getStazioneArrivo().toLowerCase();

        CercaTratteResponse.Builder responseBuilder = CercaTratteResponse.newBuilder();

        for (Tratta tratta : tratteDisponibili) {
            if (tratta.getStazionePartenza().toLowerCase().contains(partenza)
                    && tratta.getStazioneArrivo().toLowerCase().contains(arrivo)) {
                responseBuilder.addTratte(tratta);
            }
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void acquistaBiglietto(AcquistaBigliettoRequest request, StreamObserver<AcquistaBigliettoResponse> responseObserver) {
        String idTratta = request.getIdTratta();

        boolean esiste = tratteDisponibili.stream().anyMatch(t -> t.getId().equals(idTratta));

        AcquistaBigliettoResponse response = AcquistaBigliettoResponse.newBuilder()
                .setSuccesso(esiste)
                .setMessaggio(esiste ? "Biglietto acquistato con successo!" : "Tratta non trovata.")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
