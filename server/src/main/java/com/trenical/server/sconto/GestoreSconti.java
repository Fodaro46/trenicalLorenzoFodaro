package com.trenical.server.sconto;
import com.trenical.grpc.OffertaResponse;
import com.trenical.grpc.Tratta;
import java.util.ArrayList;
import java.util.List;

public class GestoreSconti {
    private final List<StrategiaSconto> strategie = new ArrayList<>();
    public GestoreSconti() {
        strategie.add(new ScontoStudente());
        strategie.add(new ScontoWeekend());
        strategie.add(new ScontoLastMinute());
        strategie.add(new ScontoFedelta());
    }

    public OffertaResponse calcolaMiglioreOfferta(Tratta tratta, String userId) {
        StrategiaSconto migliore = null;
        double migliorPrezzo = tratta.getPrezzo();

        for (StrategiaSconto s : strategie) {
            if (s.isApplicabile(tratta, userId)) {
                double scontato = s.calcolaPrezzoScontato(tratta);
                if (scontato < migliorPrezzo) {
                    migliorPrezzo = scontato;
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
