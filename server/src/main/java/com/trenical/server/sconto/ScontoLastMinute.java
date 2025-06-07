package com.trenical.server.sconto;
import com.trenical.grpc.Tratta;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScontoLastMinute implements StrategiaSconto {

    @Override
    public boolean isApplicabile(Tratta tratta, String userId) {
        try {
            LocalTime partenza = LocalTime.parse(tratta.getOrarioPartenza(), DateTimeFormatter.ofPattern("HH:mm"));
            return LocalTime.now().plusHours(2).isAfter(partenza);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public double calcolaPrezzoScontato(Tratta tratta) {
        return tratta.getPrezzo() * 0.75;
    }

    @Override
    public String getNome() {
        return "Sconto Last Minute";
    }

    @Override
    public String getDescrizione() {
        return "25% di sconto se la partenza è entro 2 ore";
    }
}
