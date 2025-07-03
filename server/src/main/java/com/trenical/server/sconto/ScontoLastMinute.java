package com.trenical.server.sconto;
import com.trenical.grpc.Tratta;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScontoLastMinute implements StrategiaSconto {
    @Override
    public boolean isApplicabile(ContestoSconto ctx) {
        if (!ctx.getDataAcquisto().equals(ctx.getDataViaggio())) return false;
        return LocalTime.now().plusHours(2).isAfter(ctx.getOrarioPartenza());
    }

    @Override
    public double calcolaPrezzoScontato(ContestoSconto ctx) {
        return ctx.getTratta().getPrezzo() * 0.75;
    }

    public String getNome() { return "Sconto Last Minute"; }

    public String getDescrizione() { return "25% di sconto se il treno parte entro 2 ore"; }
}

