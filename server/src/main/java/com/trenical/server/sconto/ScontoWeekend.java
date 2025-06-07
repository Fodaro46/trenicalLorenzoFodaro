package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class ScontoWeekend implements StrategiaSconto {

    @Override
    public boolean isApplicabile(Tratta tratta, String userId) {
        DayOfWeek oggi = LocalDate.now().getDayOfWeek();
        return oggi == DayOfWeek.SATURDAY || oggi == DayOfWeek.SUNDAY;
    }

    @Override
    public double calcolaPrezzoScontato(Tratta tratta) {
        return tratta.getPrezzo() * 0.85;
    }

    @Override
    public String getNome() {
        return "Sconto Weekend";
    }

    @Override
    public String getDescrizione() {
        return "15% di sconto nel weekend";
    }
}
