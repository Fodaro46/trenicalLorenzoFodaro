package com.trenical.server;

import com.trenical.grpc.*;
import io.grpc.stub.StreamObserver;

public class TrenicalServiceImpl extends TrenicalServiceGrpc.TrenicalServiceImplBase {

    @Override
    public void getTicketInfo(TicketRequest request, StreamObserver<TicketResponse> responseObserver) {
        TicketResponse response = TicketResponse.newBuilder()
                .setTicketId(request.getTicketId())
                .setPassengerName("Mario Rossi")
                .setTrainNumber("FR9432")
                .setDepartureTime("10:30")
                .setArrivalTime("13:45")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        LoginResponse response = LoginResponse.newBuilder()
                .setUserId("user-" + request.getEmail().hashCode())
                .setMessage("Login effettuato con successo per " + request.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void acquistaBiglietto(BigliettoRequest request, StreamObserver<BigliettoResponse> responseObserver) {
        BigliettoResponse response = BigliettoResponse.newBuilder()
                .setBigliettoId("B-" + Math.abs(request.getUserId().hashCode()) + "-" + System.currentTimeMillis())
                .setStato("Confermato")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    // Gli altri metodi possono essere implementati nei prossimi sprint:
    // streamNotifiche() ecc.
}
