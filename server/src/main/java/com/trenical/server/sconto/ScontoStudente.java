package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;

public class ScontoStudente implements StrategiaSconto {
    @Override
    public boolean isApplicabile(ContestoSconto ctx) {
        return ctx.getUtente() != null && ctx.getUtente().isStudente(); // usa modello utente reale
    }

    @Override
    public double calcolaPrezzoScontato(ContestoSconto ctx) {
        return ctx.getTratta().getPrezzo() * 0.80;
    }

    public String getNome() { return "Sconto Studente"; }

    public String getDescrizione() { return "20% di sconto per studenti"; }
}
