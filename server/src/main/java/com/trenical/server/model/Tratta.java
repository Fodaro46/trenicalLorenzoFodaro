package com.trenical.server.model;

public class Tratta {
    private int schemaVersion = 1;

    private String id;
    private String stazionePartenza;
    private String stazioneArrivo;
    private String orarioPartenza;
    private String orarioArrivo;
    private double prezzo;

    public Tratta() {}

    public Tratta(String id, String stazionePartenza, String stazioneArrivo,
                  String orarioPartenza, String orarioArrivo, double prezzo) {
        this.schemaVersion = 1;
        this.id = id;
        this.stazionePartenza = stazionePartenza;
        this.stazioneArrivo = stazioneArrivo;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.prezzo = prezzo;
    }

    public int getSchemaVersion() {
        return schemaVersion;
    }
    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStazionePartenza() { return stazionePartenza; }
    public void setStazionePartenza(String stazionePartenza) {
        this.stazionePartenza = stazionePartenza;
    }

    public String getStazioneArrivo() { return stazioneArrivo; }
    public void setStazioneArrivo(String stazioneArrivo) {
        this.stazioneArrivo = stazioneArrivo;
    }

    public String getOrarioPartenza() { return orarioPartenza; }
    public void setOrarioPartenza(String orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public String getOrarioArrivo() { return orarioArrivo; }
    public void setOrarioArrivo(String orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
}
