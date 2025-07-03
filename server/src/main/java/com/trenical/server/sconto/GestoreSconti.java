package com.trenical.server.sconto;

import com.trenical.grpc.OffertaResponse;
import com.trenical.grpc.Tratta;
import com.trenical.server.model.Utente;
import com.trenical.server.repository.UtenteRepository;

import java.util.List;

public class GestoreSconti {

    private final List<StrategiaSconto> strategie = List.of(
            new ScontoFedelta(),
            new ScontoStudente(),
            new ScontoWeekend(),
            new ScontoLastMinute()
    );

    public OffertaResponse calcolaMiglioreOfferta(Tratta tratta, String userId) {
        Utente utente = UtenteRepository.caricaPerId(userId);
        ContestoSconto ctx;

        try {
            ctx = new ContestoSconto(tratta, utente);
        } catch (Exception e) {
            System.err.println("❌ Errore nella creazione del contesto sconto: " + e.getMessage());
            return OffertaResponse.newBuilder()
                    .setTipo("Errore")
                    .setPrezzoScontato(tratta.getPrezzo())
                    .setDescrizione("Errore nel calcolo dell'offerta")
                    .build();
        }

        StrategiaSconto migliore = null;
        double migliorPrezzo = tratta.getPrezzo();

        for (StrategiaSconto s : strategie) {
            if (s.isApplicabile(ctx)) {
                double prezzo = s.calcolaPrezzoScontato(ctx);
                if (prezzo < migliorPrezzo) {
                    migliorPrezzo = prezzo;
                    migliore = s;
                }
            }
        }

        if (migliore != null) {
            return OffertaResponse.newBuilder()
                    .setTipo(migliore.getNome())
                    .setPrezzoScontato(migliorPrezzo)
                    .setDescrizione(migliore.getDescrizione())
                    .build();
        }

        return OffertaResponse.newBuilder()
                .setTipo("Nessuna offerta")
                .setPrezzoScontato(tratta.getPrezzo())
                .setDescrizione("Prezzo pieno")
                .build();
    }
}
