package com.trenical.server.service;

import com.google.protobuf.Empty;
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

import java.time.LocalDateTime;
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
        String orario = req.getOrario();
        String data = req.getData();

        Tratta scelta = TrattaRepository.caricaTratte().stream()
                .filter(t -> t.getId().equals(req.getTratta()))
                .findFirst().orElse(null);

        if (scelta == null) {
            obs.onNext(BigliettoResponse.newBuilder()
                    .setBigliettoId("ERR")
                    .setStato("❌ Tratta non trovata")
                    .setPrezzo(0)
                    .setTrattaId("-")
                    .setData(data)
                    .setOrario(orario)
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
                                .setData(data)
                                .build(), uid)
                .getPrezzoScontato();

        Biglietto b = new Biglietto(
                "B-" + Math.abs(uid.hashCode()) + "-" + System.currentTimeMillis(),
                uid,
                scelta.getId(),
                scelta.getStazionePartenza(),
                scelta.getStazioneArrivo(),
                prezzo,
                data,
                orario
        );
        BigliettoRepository.salvaBiglietto(b);

        NotificationRegistry.addNotification(uid,
                Notifica.newBuilder()
                        .setId("N-" + UUID.randomUUID())
                        .setUserId(uid)
                        .setMessaggio("🎟️ Acquisto: " + scelta.getStazionePartenza() + "→" + scelta.getStazioneArrivo()
                                + " il " + data + " ore " + orario + " | € " + String.format("%.2f", prezzo))
                        .setTimestamp(LocalDateTime.now().toString())
                        .build()
        );

        obs.onNext(BigliettoResponse.newBuilder()
                .setBigliettoId(b.getId())
                .setStato("Confermato")
                .setPrezzo(prezzo)
                .setTrattaId(scelta.getId())
                .setData(data)
                .setOrario(orario)
                .build());
        obs.onCompleted();
    }

    @Override
    public void cercaTratte(CercaTratteRequest req, StreamObserver<CercaTratteResponse> obs) {
        String partenza = req.getStazionePartenza().toLowerCase();
        String arrivo = req.getStazioneArrivo().toLowerCase();

        CercaTratteResponse.Builder response = CercaTratteResponse.newBuilder();

        for (Tratta t : TrattaRepository.caricaTratte()) {
            if (t.getStazionePartenza().toLowerCase().contains(partenza)
                    && t.getStazioneArrivo().toLowerCase().contains(arrivo)) {
                response.addTratte(com.trenical.grpc.Tratta.newBuilder()
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

        obs.onNext(response.build());
        obs.onCompleted();
    }

    @Override
    public void getStoricoBiglietti(UserIdRequest req, StreamObserver<StoricoBigliettiResponse> obs) {
        StoricoBigliettiResponse.Builder builder = StoricoBigliettiResponse.newBuilder();

        for (Biglietto bg : BigliettoRepository.caricaBiglietti()) {
            if (bg.getUserId().equals(req.getUserId())) {
                builder.addBiglietti(BigliettoInfo.newBuilder()
                        .setId(bg.getId())
                        .setTrattaId(bg.getTrattaId())
                        .setPartenza(bg.getPartenza())
                        .setArrivo(bg.getArrivo())
                        .setPrezzo(bg.getPrezzo())
                        .setTimestamp(bg.getTimestamp())
                        .setOrario(bg.getOrario() != null ? bg.getOrario() : "??:??")
                        .build());
            }
        }

        obs.onNext(builder.build());
        obs.onCompleted();
    }

    @Override
    public void streamNotifiche(NotificheRequest req, StreamObserver<Notifica> obs) {
        String uid = req.getUserId();

        if (!StreamManager.isConnesso(uid)) {
            StreamManager.registra(uid, obs);
            System.out.println("[DEBUG] 🔔 Listener aggiunto per utente: " + uid);
        }

        NotificationRegistry.markAllAsRead(uid);

        List<Notifica> pendenti = NotificationRegistry.getUnreadNotifications(uid);
        for (Notifica n : pendenti) {
            obs.onNext(n);
        }
    }

    @Override
    public void updateTratta(UpdateTrattaRequest req, StreamObserver<Empty> obs) {
        List<Tratta> tratte = TrattaRepository.caricaTratte();
        com.trenical.grpc.Tratta g = req.getTratta();

        tratte = tratte.stream()
                .map(t -> t.getId().equals(g.getId())
                        ? new Tratta(g.getId(), g.getStazionePartenza(), g.getStazioneArrivo(),
                        g.getOrarioPartenza(), g.getOrarioArrivo(), g.getPrezzo())
                        : t)
                .collect(Collectors.toList());

        TrattaRepository.salvaTratte(tratte);

        List<Biglietto> tickets = BigliettoRepository.caricaBiglietti();

        for (Utente u : UtenteRepository.caricaTutti()) {
            boolean haBiglietti = tickets.stream()
                    .anyMatch(b -> b.getUserId().equals(u.getUserId())
                            && b.getTrattaId().equals(g.getId()));

            if (haBiglietti) {
                Notifica n = Notifica.newBuilder()
                        .setId("N-" + UUID.randomUUID())
                        .setUserId(u.getUserId())
                        .setMessaggio("🔄 Tratta aggiornata: " + g.getStazionePartenza() + " → " + g.getStazioneArrivo())
                        .setTimestamp(LocalDateTime.now().toString())
                        .build();
                NotificationRegistry.addNotification(u.getUserId(), n);
            }
        }

        obs.onNext(Empty.getDefaultInstance());
        obs.onCompleted();
    }
}
