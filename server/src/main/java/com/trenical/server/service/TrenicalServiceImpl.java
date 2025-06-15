package com.trenical.server.service;

import com.trenical.grpc.*;
import com.trenical.server.model.Biglietto;
import com.trenical.server.model.Tratta;
import com.trenical.server.model.Utente;
import com.trenical.server.repository.BigliettoRepository;
import com.trenical.server.repository.TrattaRepository;
import com.trenical.server.repository.UtenteRepository;
import com.trenical.server.sconto.GestoreSconti;
import com.trenical.server.util.NotificationRegistry;
import com.trenical.server.util.StreamManager;
import io.grpc.stub.StreamObserver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrenicalServiceImpl extends TrenicalServiceGrpc.TrenicalServiceImplBase {

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        String email = request.getEmail();
        System.out.println("🔐 [LOGIN] Tentativo con email: " + email);

        Utente utente = UtenteRepository.caricaPerEmail(email);
        if (utente == null) {
            String userId = "user-" + Math.abs(email.hashCode());
            utente = new Utente(userId, email);
            UtenteRepository.salvaUtente(utente);
            System.out.println("➕ [LOGIN] Nuovo utente creato: " + userId);
        } else {
            System.out.println("✅ [LOGIN] Utente esistente: " + utente.getUserId());
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

        System.out.println("🛒 [ACQUISTO] Richiesta da user " + userId + " per tratta " + idTratta);

        Tratta scelta = TrattaRepository.caricaTratte().stream()
                .filter(t -> t.getId().equals(idTratta))
                .findFirst()
                .orElse(null);

        if (scelta == null) {
            System.out.println("❌ [ACQUISTO] Tratta non trovata: " + idTratta);
            responseObserver.onNext(BigliettoResponse.newBuilder()
                    .setBigliettoId("ERR")
                    .setStato("❌ Tratta non trovata")
                    .setPrezzo(0.0)
                    .build());
            responseObserver.onCompleted();
            return;
        }

        com.trenical.grpc.Tratta trattaGrpc = com.trenical.grpc.Tratta.newBuilder()
                .setId(scelta.getId())
                .setStazionePartenza(scelta.getStazionePartenza())
                .setStazioneArrivo(scelta.getStazioneArrivo())
                .setOrarioPartenza(scelta.getOrarioPartenza())
                .setOrarioArrivo(scelta.getOrarioArrivo())
                .setPrezzo(scelta.getPrezzo())
                .setData(request.getData())
                .build();

        GestoreSconti gestore = new GestoreSconti();
        double prezzoFinale = gestore.calcolaMiglioreOfferta(trattaGrpc, userId).getPrezzoScontato();

        Biglietto biglietto = new Biglietto(
                "B-" + Math.abs(userId.hashCode()) + "-" + System.currentTimeMillis(),
                userId,
                scelta.getId(),
                scelta.getStazionePartenza(),
                scelta.getStazioneArrivo(),
                prezzoFinale,
                request.getData()
        );

        BigliettoRepository.salvaBiglietto(biglietto);
        System.out.println("✅ [ACQUISTO] Biglietto salvato: " + biglietto.getId());

        NotificationRegistry.addNotification(userId,
                Notifica.newBuilder()
                        .setUserId(userId)  // 🔥 FONDAMENTALE PER FUNZIONARE
                        .setMessaggio("🎟️ Acquisto: " + scelta.getStazionePartenza() + "→" + scelta.getStazioneArrivo()
                                + " il " + request.getData() + " | € " + String.format("%.2f", prezzoFinale))
                        .setTimestamp(LocalTime.now().toString())
                        .build()
        );

        BigliettoResponse response = BigliettoResponse.newBuilder()
                .setBigliettoId(biglietto.getId())
                .setStato("Confermato")
                .setPrezzo(prezzoFinale)
                .setTrattaId(scelta.getId())
                .setData(request.getData())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void cercaTratte(CercaTratteRequest request, StreamObserver<CercaTratteResponse> responseObserver) {
        String partenza = request.getStazionePartenza().toLowerCase();
        String arrivo = request.getStazioneArrivo().toLowerCase();

        System.out.println("🔍 [RICERCA] Cerca da: " + partenza + " a " + arrivo);

        CercaTratteResponse.Builder builder = CercaTratteResponse.newBuilder();

        for (Tratta t : TrattaRepository.caricaTratte()) {
            if (t.getStazionePartenza().toLowerCase().contains(partenza)
                    && t.getStazioneArrivo().toLowerCase().contains(arrivo)) {
                builder.addTratte(com.trenical.grpc.Tratta.newBuilder()
                        .setId(t.getId())
                        .setStazionePartenza(t.getStazionePartenza())
                        .setStazioneArrivo(t.getStazioneArrivo())
                        .setOrarioPartenza(t.getOrarioPartenza())
                        .setOrarioArrivo(t.getOrarioArrivo())
                        .setPrezzo(t.getPrezzo())
                        .setData("")
                        .build());
            }
        }

        System.out.println("🔍 [RICERCA] Trovate " + builder.getTratteCount() + " tratte");

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStoricoBiglietti(UserIdRequest request, StreamObserver<StoricoBigliettiResponse> responseObserver) {
        String userId = request.getUserId();
        List<Biglietto> miei = BigliettoRepository.caricaBiglietti().stream()
                .filter(b -> b.getUserId().equals(userId))
                .toList();

        System.out.println("📜 [STORICO] Biglietti utente " + userId + ": " + miei.size());

        StoricoBigliettiResponse.Builder response = StoricoBigliettiResponse.newBuilder();
        for (Biglietto b : miei) {
            response.addBiglietti(BigliettoInfo.newBuilder()
                    .setId(b.getId())
                    .setTrattaId(b.getTrattaId())
                    .setPartenza(b.getPartenza())
                    .setArrivo(b.getArrivo())
                    .setPrezzo(b.getPrezzo())
                    .setTimestamp(b.getTimestamp())
                    .build());
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void streamNotifiche(NotificheRequest request, StreamObserver<Notifica> responseObserver) {
        String userId = request.getUserId();
        System.out.println("📤 [STREAM] Connessione notifiche per userId: " + userId);
        StreamManager.registra(userId, responseObserver);

        // ⏳ Invia solo se il canale è attivo, salva solo ID non vuoti
        List<Notifica> pending = NotificationRegistry.getUnreadNotifications(userId);
        int inviate = 0;

        for (Notifica n : pending) {
            try {
                if (n.getId().isBlank()) {
                    System.out.println("⚠️ [FIX] Assegnato nuovo ID a notifica pendente per " + userId);
                    n = n.toBuilder().setId(UUID.randomUUID().toString()).build();
                }
                responseObserver.onNext(n);
                inviate++;
            } catch (Exception e) {
                System.err.println("❌ Errore invio notifica pregressa: " + e.getMessage());
            }
        }

        if (inviate > 0) {
            System.out.println("📬 Inviate " + inviate + " notifiche pregresse a " + userId);
            NotificationRegistry.markAllAsRead(userId); // solo se almeno una è partita
        }
    }




    @Override
    public void getTicketInfo(TicketRequest request, StreamObserver<TicketResponse> responseObserver) {
        String ticketId = request.getTicketId();
        System.out.println("🔎 [INFO] Richiesta info per ticket: " + ticketId);

        Biglietto big = BigliettoRepository.caricaBiglietti().stream()
                .filter(b -> b.getId().equals(ticketId))
                .findFirst().orElse(null);

        TicketResponse.Builder resp = TicketResponse.newBuilder();

        if (big == null) {
            System.out.println("❌ [INFO] Ticket non trovato: " + ticketId);
            resp.setTicketId("N/D")
                    .setPassengerName("❌ Biglietto non trovato")
                    .setTrainNumber("N/D")
                    .setDepartureTime("N/D")
                    .setArrivalTime("N/D")
                    .setPrezzo(0.0);
        } else {
            resp.setTicketId(big.getId())
                    .setPassengerName(big.getUserId())
                    .setTrainNumber("TRN-" + big.getTrattaId())
                    .setDepartureTime(big.getTimestamp())
                    .setArrivalTime("N/D")
                    .setPrezzo(big.getPrezzo());
        }

        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }
}
