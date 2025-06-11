package com.trenical.server.service;

import com.trenical.grpc.BigliettoRequest;
import com.trenical.grpc.BigliettoResponse;
import com.trenical.grpc.LoginRequest;
import com.trenical.grpc.LoginResponse;
import com.trenical.grpc.Notifica;
import com.trenical.grpc.NotificheRequest;
import com.trenical.grpc.OffertaResponse;
import com.trenical.grpc.TicketRequest;
import com.trenical.grpc.TicketResponse;
import com.trenical.grpc.TrenicalServiceGrpc;
import com.trenical.server.model.Biglietto;
import com.trenical.server.model.Tratta;
import com.trenical.server.model.Utente;
import com.trenical.server.repository.BigliettoRepository;
import com.trenical.server.repository.TrattaRepository;
import com.trenical.server.repository.UtenteRepository;
import com.trenical.server.util.NotificationRegistry;
import io.grpc.stub.StreamObserver;

import java.time.LocalTime;
import java.util.List;

public class TrenicalServiceImpl extends TrenicalServiceGrpc.TrenicalServiceImplBase {

    @Override
    public void getTicketInfo(TicketRequest request, StreamObserver<TicketResponse> responseObserver) {
        String ticketId = request.getTicketId();

        Biglietto biglietto = BigliettoRepository.caricaBiglietti().stream()
                .filter(b -> b.getId().equals(ticketId))
                .findFirst()
                .orElse(null);

        if (biglietto == null) {
            responseObserver.onNext(TicketResponse.newBuilder()
                    .setTicketId("N/D")
                    .setPassengerName("❌ Biglietto non trovato")
                    .setTrainNumber("N/D")
                    .setDepartureTime("N/D")
                    .setArrivalTime("N/D")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        TicketResponse response = TicketResponse.newBuilder()
                .setTicketId(biglietto.getId())
                .setPassengerName(biglietto.getUserId())
                .setTrainNumber("TRN-" + biglietto.getTrattaId())
                .setDepartureTime(biglietto.getTimestamp())
                .setArrivalTime("N/D")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        String email = request.getEmail();
        System.out.println("🔐 Tentativo login con email: " + email);

        Utente utente = UtenteRepository.caricaPerEmail(email);
        if (utente == null) {
            String userId = "user-" + Math.abs(email.hashCode());
            utente = new Utente(userId, email);
            UtenteRepository.salvaUtente(utente);
            System.out.println("➕ Nuovo utente creato: " + userId);
        }

        LoginResponse response = LoginResponse.newBuilder()
                .setUserId(utente.getUserId())
                .setMessage("✅ Login effettuato per " + utente.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void acquistaBiglietto(BigliettoRequest request, StreamObserver<BigliettoResponse> responseObserver) {
        String userId = request.getUserId();
        String idTratta = request.getTratta();

        // Recupero tratte dal repository
        List<Tratta> tratte = TrattaRepository.caricaTratte();
        Tratta tratta = tratte.stream()
                .filter(t -> t.getId().equals(idTratta))
                .findFirst()
                .orElse(null);

        if (tratta == null) {
            responseObserver.onNext(BigliettoResponse.newBuilder()
                    .setBigliettoId("ERR")
                    .setStato("❌ Tratta non trovata")
                    .setPrezzo(0.0)
                    .build());
            responseObserver.onCompleted();
            return;
        }

        // Calcolo promozione
        PromotionServiceImpl promoService = new PromotionServiceImpl();
        com.trenical.grpc.Tratta grpcTratta = com.trenical.grpc.Tratta.newBuilder()
                .setId(tratta.getId())
                .setStazionePartenza(tratta.getStazionePartenza())
                .setStazioneArrivo(tratta.getStazioneArrivo())
                .setOrarioPartenza(tratta.getOrarioPartenza())
                .setOrarioArrivo(tratta.getOrarioArrivo())
                .setPrezzo(tratta.getPrezzo())
                .build();
        OffertaResponse offerta = promoService.getOffertaLocally(grpcTratta, userId);
        double prezzoScontato = offerta.getPrezzoScontato();
        System.out.println("💸 Prezzo scontato calcolato: " + prezzoScontato);

        // Costruisco e salvo biglietto
        String bigliettoId = "B-" + Math.abs(userId.hashCode()) + "-" + System.currentTimeMillis();
        Biglietto biglietto = new Biglietto(
                bigliettoId,
                userId,
                tratta.getId(),
                tratta.getStazionePartenza(),
                tratta.getStazioneArrivo(),
                prezzoScontato,
                request.getData()
        );
        BigliettoRepository.salvaBiglietto(biglietto);
        System.out.println("✅ Biglietto salvato: " + biglietto.getId());

        // Notifica evento
        NotificationRegistry.addNotification(userId,
                Notifica.newBuilder()
                        .setMessaggio("🎟️ Acquisto confermato: " + tratta.getStazionePartenza()
                                + "→" + tratta.getStazioneArrivo()
                                + " il " + request.getData())
                        .setTimestamp(LocalTime.now().toString())
                        .build());

        // Risposta al client
        BigliettoResponse response = BigliettoResponse.newBuilder()
                .setBigliettoId(bigliettoId)
                .setStato("Confermato")
                .setPrezzo(prezzoScontato)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void streamNotifiche(NotificheRequest request, StreamObserver<Notifica> responseObserver) {
        String userId = request.getUserId();
        System.out.println("📤 [STREAM] Inizio stream notifiche per: " + userId);

        List<Notifica> lista = NotificationRegistry.getNotifications(userId);
        for (Notifica n : lista) {
            responseObserver.onNext(n);
        }
        responseObserver.onCompleted();
    }
}
