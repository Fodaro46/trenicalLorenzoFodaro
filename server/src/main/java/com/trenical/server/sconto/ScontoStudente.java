package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;

public class ScontoStudente implements StrategiaSconto {

    @Override
    public boolean isApplicabile(Tratta tratta, String userId) {
        return userId != null && userId.contains("student"); // esempio banale
    }

    @Override
    public double calcolaPrezzoScontato(Tratta tratta) {
        return tratta.getPrezzo() * 0.8;
    }

    @Override
    public String getNome() {
        return "Sconto Studente";
    }

    @Override
    public String getDescrizione() {
        return "20% di sconto per studenti";
    }
}
