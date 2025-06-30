package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;
import com.trenical.server.repository.UtenteRepository;

public class ScontoFedelta implements StrategiaSconto {

    @Override
    public boolean isApplicabile(Tratta tratta, String userId) {
        var utente = UtenteRepository.caricaPerId(userId);
        return utente != null && utente.isFedelta();
    }

    @Override
    public double calcolaPrezzoScontato(Tratta tratta) {
        return tratta.getPrezzo() * 0.85;
    }

    @Override
    public String getNome() {
        return "Sconto FedeltàTreno";
    }

    @Override
    public String getDescrizione() {
        return "15% di sconto riservato ai clienti FedeltàTreno";
    }
}
