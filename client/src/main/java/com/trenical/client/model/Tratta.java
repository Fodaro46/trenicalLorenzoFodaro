package com.trenical.client.model;

public class Tratta {
    private String id;
    private String stazionePartenza;
    private String stazioneArrivo;
    private String orarioPartenza;
    private String orarioArrivo;
    private double prezzo;

    public Tratta(String id, String stazionePartenza, String stazioneArrivo, String orarioPartenza, String orarioArrivo, double prezzo) {
        this.id = id;
        this.stazionePartenza = stazionePartenza;
        this.stazioneArrivo = stazioneArrivo;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.prezzo = prezzo;
    }

    // Getter necessari per TableView
    public String getId() { return id; }
    public String getStazionePartenza() { return stazionePartenza; }
    public String getStazioneArrivo() { return stazioneArrivo; }
    public String getOrarioPartenza() { return orarioPartenza; }
    public String getOrarioArrivo() { return orarioArrivo; }
    public double getPrezzo() { return prezzo; }
}
