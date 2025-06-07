package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;

public interface StrategiaSconto {
    boolean isApplicabile(Tratta tratta, String userId);
    double calcolaPrezzoScontato(Tratta tratta);
    String getNome();
    String getDescrizione();
}
