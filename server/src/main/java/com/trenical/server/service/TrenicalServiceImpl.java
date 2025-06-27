package com.trenical.server.service;

import com.google.protobuf.Empty;
import com.trenical.grpc.CercaTratteRequest;
import com.trenical.grpc.CercaTratteResponse;
import com.trenical.grpc.BigliettoRequest;
import com.trenical.grpc.BigliettoResponse;
import com.trenical.grpc.LoginRequest;
import com.trenical.grpc.LoginResponse;
import com.trenical.grpc.NotificheRequest;
import com.trenical.grpc.Notifica;
import com.trenical.grpc.TicketRequest;
import com.trenical.grpc.TicketResponse;
import com.trenical.grpc.TrenicalServiceGrpc;
import com.trenical.grpc.UpdateTrattaRequest;
import com.trenical.grpc.UserIdRequest;
import com.trenical.grpc.StoricoBigliettiResponse;
import com.trenical.grpc.BigliettoInfo;
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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrenicalServiceImpl extends TrenicalServiceGrpc.TrenicalServiceImplBase {

    @Override
    public void login(LoginRequest req, StreamObserver<LoginResponse> obs) {
        String email = req.getEmail();
        Utente u = UtenteRepository.caricaPerEmail(email);
        if (u == null) {
            String id = "user-" + Math.abs(email.hashCode());
            u = new Utente(id, email);
            UtenteRepository.salvaUtente(u);
        }
        LoginResponse resp = LoginResponse.newBuilder()
                .setUserId(u.getUserId())
                .setMessage("✅ Login effettuato per " + u.getEmail())
                .build();
        obs.onNext(resp);
        obs.onCompleted();
    }

    @Override
    public void acquistaBiglietto(BigliettoRequest req, StreamObserver<BigliettoResponse> obs) {
        String uid = req.getUserId();
        Tratta scelta = TrattaRepository.caricaTratte().stream()
                .filter(t -> t.getId().equals(req.getTratta()))
                .findFirst().orElse(null);
        if (scelta == null) {
            obs.onNext(BigliettoResponse.newBuilder()
                    .setBigliettoId("ERR")
                    .setStato("❌ Tratta non trovata")
                    .setPrezzo(0)
                    .build());
            obs.onCompleted();
            return;
        }
        double prezzo = new GestoreSconti()
                .calcolaMiglioreOfferta(
                        com.trenical.grpc.Tratta.newBuilder()
                                .setId(scelta.getId())
                                .setStazionePartenza(scelta.getStazionePartenza())
                                .setStazioneArrivo(scelta.getStazioneArrivo())
                                .setOrarioPartenza(scelta.getOrarioPartenza())
                                .setOrarioArrivo(scelta.getOrarioArrivo())
                                .setPrezzo(scelta.getPrezzo())
                                .setData(req.getData())
                                .build(), uid)
                .getPrezzoScontato();
        Biglietto b = new Biglietto(
                "B-" + Math.abs(uid.hashCode()) + "-" + System.currentTimeMillis(),
                uid, scelta.getId(), scelta.getStazionePartenza(), scelta.getStazioneArrivo(),
                prezzo, req.getData());
        BigliettoRepository.salvaBiglietto(b);
        // notifica acquisto
        NotificationRegistry.addNotification(uid,
                Notifica.newBuilder()
                        .setId("")
                        .setUserId(uid)
                        .setMessaggio("🎟️ Acquisto: " + scelta.getStazionePartenza() + "→" + scelta.getStazioneArrivo()
                                + " il " + req.getData() + " | € " + String.format("%.2f", prezzo))
                        .setTimestamp(LocalTime.now().toString())
                        .build()
        );
        obs.onNext(BigliettoResponse.newBuilder()
                .setBigliettoId(b.getId())
                .setStato("Confermato")
                .setPrezzo(prezzo)
                .setTrattaId(scelta.getId())
                .setData(req.getData())
                .build());
        obs.onCompleted();
    }

    @Override
    public void cercaTratte(CercaTratteRequest req, StreamObserver<CercaTratteResponse> obs) {
        String p = req.getStazionePartenza().toLowerCase();
        String a = req.getStazioneArrivo().toLowerCase();
        CercaTratteResponse.Builder b = CercaTratteResponse.newBuilder();
        for (Tratta t : TrattaRepository.caricaTratte()) {
            if (t.getStazionePartenza().toLowerCase().contains(p)
                    && t.getStazioneArrivo().toLowerCase().contains(a)) {
                b.addTratte(com.trenical.grpc.Tratta.newBuilder()
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
        obs.onNext(b.build());
        obs.onCompleted();
    }

    @Override
    public void getStoricoBiglietti(UserIdRequest req, StreamObserver<StoricoBigliettiResponse> obs) {
        StoricoBigliettiResponse.Builder b = StoricoBigliettiResponse.newBuilder();
        for (Biglietto bg : BigliettoRepository.caricaBiglietti()) {
            if (bg.getUserId().equals(req.getUserId())) {
                b.addBiglietti(BigliettoInfo.newBuilder()
                        .setId(bg.getId())
                        .setTrattaId(bg.getTrattaId())
                        .setPartenza(bg.getPartenza())
                        .setArrivo(bg.getArrivo())
                        .setPrezzo(bg.getPrezzo())
                        .setTimestamp(bg.getTimestamp())
                        .build());
            }
        }
        obs.onNext(b.build());
        obs.onCompleted();
    }

    @Override
    public void streamNotifiche(NotificheRequest req, StreamObserver<Notifica> obs) {
        String uid = req.getUserId();
        // registro subito lo stream
        StreamManager.registra(uid, obs);
        // invio tutte le pendenti
        List<Notifica> pend = NotificationRegistry.getUnreadNotifications(uid);
        for (Notifica n : pend) {
            obs.onNext(n);
        }
        // segno lette
        NotificationRegistry.markAllAsRead(uid);
        // non chiudo il flusso
    }

    @Override
    public void updateTratta(UpdateTrattaRequest req, StreamObserver<Empty> obs) {
        // aggiorno la lista delle tratte
        List<Tratta> tratte = TrattaRepository.caricaTratte();
        com.trenical.grpc.Tratta g = req.getTratta();
        tratte = tratte.stream()
                .map(t -> t.getId().equals(g.getId())
                        ? new Tratta(g.getId(), g.getStazionePartenza(), g.getStazioneArrivo(),
                        g.getOrarioPartenza(), g.getOrarioArrivo(), g.getPrezzo())
                        : t)
                .collect(Collectors.toList());
        TrattaRepository.salvaTratte(tratte);
        // notifico i clienti che hanno un biglietto su questa tratta
        List<Biglietto> tickets = BigliettoRepository.caricaBiglietti();
        for (Utente u : UtenteRepository.caricaTutti()) {
            boolean has = tickets.stream()
                    .anyMatch(b -> b.getUserId().equals(u.getUserId())
                            && b.getTrattaId().equals(g.getId()));
            if (has) {
                Notifica n = Notifica.newBuilder()
                        .setId("N-" + UUID.randomUUID())
                        .setUserId(u.getUserId())
                        .setMessaggio("🔄 Tratta aggiornata: "
                                + g.getStazionePartenza() + " → " + g.getStazioneArrivo())
                        .setTimestamp(LocalDateTime.now().toString())
                        .build();
                NotificationRegistry.addNotification(u.getUserId(), n);
            }
        }
        obs.onNext(Empty.getDefaultInstance());
        obs.onCompleted();
    }
}
