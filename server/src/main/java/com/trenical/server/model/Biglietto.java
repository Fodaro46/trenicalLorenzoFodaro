package com.trenical.server.model;

public class Biglietto {
    private int schemaVersion = 1;

    private String id;
    private String userId;
    private String trattaId;
    private String partenza;
    private String arrivo;
    private double prezzo;
    private String timestamp;
    private String orario;


    public Biglietto(String id, String userId, String trattaId,
                     String partenza, String arrivo,
                     double prezzo, String timestamp, String orario) {
        this.schemaVersion = 1;
        this.id = id;
        this.userId = userId;
        this.trattaId = trattaId;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.prezzo = prezzo;
        this.timestamp = timestamp;
        this.orario = orario;
    }

    public int getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTrattaId() { return trattaId; }
    public void setTrattaId(String trattaId) { this.trattaId = trattaId; }

    public String getPartenza() { return partenza; }
    public void setPartenza(String partenza) { this.partenza = partenza; }

    public String getArrivo() { return arrivo; }
    public void setArrivo(String arrivo) { this.arrivo = arrivo; }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getOrario() { return orario; }
    public void setOrario(String orario) { this.orario = orario; }
}
