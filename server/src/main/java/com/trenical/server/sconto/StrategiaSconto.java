package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;

public interface StrategiaSconto {
    boolean isApplicabile(ContestoSconto contesto);
    double calcolaPrezzoScontato(ContestoSconto contesto);
    String getNome();
    String getDescrizione();
}
