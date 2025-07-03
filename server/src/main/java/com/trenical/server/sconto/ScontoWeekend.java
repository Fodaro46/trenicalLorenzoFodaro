package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class ScontoWeekend implements StrategiaSconto {
    @Override
    public boolean isApplicabile(ContestoSconto ctx) {
        int day = ctx.getDataAcquisto().getDayOfWeek().getValue(); // 6=Sabato, 7=Domenica
        return day == 6 || day == 7;
    }

    @Override
    public double calcolaPrezzoScontato(ContestoSconto ctx) {
        return ctx.getTratta().getPrezzo() * 0.90;
    }

    public String getNome() { return "Sconto Weekend"; }

    public String getDescrizione() { return "10% di sconto se acquisti nel weekend"; }
}

