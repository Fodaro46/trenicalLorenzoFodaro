package com.trenical.server.model;

public class Treno {
    private String codice;
    private String partenza;
    private String arrivo;
    private String orario;

    public Treno(String codice, String partenza, String arrivo, String orario) {
        this.codice = codice;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.orario = orario;
    }

    public String getCodice() {
        return codice;
    }

    public String getPartenza() {
        return partenza;
    }

    public String getArrivo() {
        return arrivo;
    }

    public String getOrario() {
        return orario;
    }
}
