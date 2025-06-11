package com.trenical.server.model;

public class Biglietto {
    private String id;
    private String userId;
    private String trattaId;
    private String partenza;
    private String arrivo;
    private double prezzo;
    private String timestamp;


    public Biglietto() {}

    public Biglietto(String id, String userId, String trattaId, String partenza, String arrivo, double prezzo, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.trattaId = trattaId;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.prezzo = prezzo;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getTrattaId() { return trattaId; }
    public String getPartenza() { return partenza; }
    public String getArrivo() { return arrivo; }
    public double getPrezzo() { return prezzo; }
    public String getTimestamp() { return timestamp; }

    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTrattaId(String trattaId) { this.trattaId = trattaId; }
    public void setPartenza(String partenza) { this.partenza = partenza; }
    public void setArrivo(String arrivo) { this.arrivo = arrivo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
