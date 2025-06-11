package com.trenical.server.service;

import common.AcquistaBigliettoRequest;
import common.AcquistaBigliettoResponse;
import common.CercaTratteRequest;
import common.CercaTratteResponse;
import common.BigliettoInfo;
import common.StoricoBigliettiResponse;
import common.TrattaServiceGrpc;
import common.UserIdRequest;
import com.trenical.server.model.Biglietto;
import com.trenical.server.model.Tratta;
import com.trenical.server.repository.BigliettoRepository;
import com.trenical.server.repository.TrattaRepository;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.UUID;

public class TrattaServiceImpl extends TrattaServiceGrpc.TrattaServiceImplBase {

    @Override
    public void cercaTratte(CercaTratteRequest request,
                            StreamObserver<CercaTratteResponse> responseObserver) {
        String partenza = request.getStazionePartenza().toLowerCase();
        String arrivo   = request.getStazioneArrivo().toLowerCase();
        // Data filter is available in request.getData() if needed

        List<Tratta> tratte = TrattaRepository.caricaTratte();
        CercaTratteResponse.Builder builder = CercaTratteResponse.newBuilder();
        for (Tratta t : tratte) {
            if (t.getStazionePartenza().toLowerCase().contains(partenza)
                    && t.getStazioneArrivo().toLowerCase().contains(arrivo)) {
                builder.addTratte(
                        common.Tratta.newBuilder()
                                .setId(t.getId())
                                .setStazionePartenza(t.getStazionePartenza())
                                .setStazioneArrivo(t.getStazioneArrivo())
                                .setOrarioPartenza(t.getOrarioPartenza())
                                .setOrarioArrivo(t.getOrarioArrivo())
                                .setPrezzo(t.getPrezzo())
                                .build()
                );
            }
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void acquistaBiglietto(AcquistaBigliettoRequest request,
                                  StreamObserver<AcquistaBigliettoResponse> responseObserver) {
        String idTratta = request.getIdTratta();
        String userId   = request.getUserId();

        List<Tratta> tratte = TrattaRepository.caricaTratte();
        Tratta scelta = tratte.stream()
                .filter(t -> t.getId().equals(idTratta))
                .findFirst().orElse(null);

        if (scelta == null) {
            responseObserver.onNext(
                    AcquistaBigliettoResponse.newBuilder()
                            .setSuccesso(false)
                            .setMessaggio("Tratta non trovata")
                            .build()
            );
            responseObserver.onCompleted();
            return;
        }

        // Salvo il biglietto
        Biglietto b = new Biglietto(
                UUID.randomUUID().toString(),
                userId,
                scelta.getId(),
                scelta.getStazionePartenza(),
                scelta.getStazioneArrivo(),
                scelta.getPrezzo(),
                request.getData()
        );
        BigliettoRepository.salvaBiglietto(b);

        responseObserver.onNext(
                AcquistaBigliettoResponse.newBuilder()
                        .setSuccesso(true)
                        .setMessaggio("Biglietto confermato, ID: " + b.getId())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getStoricoBiglietti(UserIdRequest request,
                                    StreamObserver<StoricoBigliettiResponse> responseObserver) {
        String userId = request.getUserId();
        List<Biglietto> tickets = BigliettoRepository.getByUserId(userId);

        StoricoBigliettiResponse.Builder builder = StoricoBigliettiResponse.newBuilder();
        for (Biglietto big : tickets) {
            builder.addBiglietti(
                    BigliettoInfo.newBuilder()
                            .setId(big.getId())
                            .setTrattaId(big.getTrattaId())
                            .setPartenza(big.getPartenza())
                            .setArrivo(big.getArrivo())
                            .setPrezzo(big.getPrezzo())
                            .setTimestamp(big.getTimestamp())
                            .build()
            );
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
