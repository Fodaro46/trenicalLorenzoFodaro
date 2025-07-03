package com.trenical.client.model;

public class Tratta {
    private final String id;
    private final String stazionePartenza;
    private final String stazioneArrivo;
    private final String data;
    private final String orarioPartenza;
    private final String orarioArrivo;
    private final double prezzo;

    public Tratta(String id, String stazionePartenza, String stazioneArrivo,
                  String data, String orarioPartenza, String orarioArrivo, double prezzo) {
        this.id = id;
        this.stazionePartenza = stazionePartenza;
        this.stazioneArrivo = stazioneArrivo;
        this.data = data;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.prezzo = prezzo;
    }

    public String getId() { return id; }
    public String getStazionePartenza() { return stazionePartenza; }
    public String getStazioneArrivo() { return stazioneArrivo; }
    public String getData() { return data; }
    public String getOrarioPartenza() { return orarioPartenza; }
    public String getOrarioArrivo() { return orarioArrivo; }
    public double getPrezzo() { return prezzo; }
}
