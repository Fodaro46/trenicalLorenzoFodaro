package com.trenical.server.service;

import com.trenical.grpc.GetOffertaRequest;
import com.trenical.grpc.OffertaResponse;
import com.trenical.grpc.PromotionServiceGrpc;
import com.trenical.grpc.Tratta;
import com.trenical.server.sconto.GestoreSconti;
import io.grpc.stub.StreamObserver;

public class PromotionServiceImpl extends PromotionServiceGrpc.PromotionServiceImplBase {

    private final GestoreSconti gestore = new GestoreSconti();

    @Override
    public void getOfferta(GetOffertaRequest request, StreamObserver<OffertaResponse> responseObserver) {
        Tratta tratta = request.getTratta();
        String userId = request.getUserId();

        OffertaResponse offerta = gestore.calcolaMiglioreOfferta(tratta, userId);
        responseObserver.onNext(offerta);
        responseObserver.onCompleted();
    }

    public OffertaResponse getOffertaLocally(Tratta tratta, String userId) {
        System.out.println("🎁 Offerta generata per tratta " + tratta.getId() + " e utente " + userId);
        return gestore.calcolaMiglioreOfferta(tratta, userId);
    }
}
