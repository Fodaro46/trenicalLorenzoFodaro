package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;
import com.trenical.server.repository.UtenteRepository;

public class ScontoFedelta implements StrategiaSconto {
    @Override
    public boolean isApplicabile(ContestoSconto ctx) {
        return ctx.getUtente() != null && ctx.getUtente().isFedelta();
    }

    @Override
    public double calcolaPrezzoScontato(ContestoSconto ctx) {
        return ctx.getTratta().getPrezzo() * 0.85;
    }

    public String getNome() { return "Sconto FedeltàTreno"; }

    public String getDescrizione() { return "15% di sconto riservato ai clienti FedeltàTreno"; }
}


