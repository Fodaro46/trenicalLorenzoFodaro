package com.trenical.client.model;

public class Ticket {
    private String id;
    private String tratta;
    private String data;
    private String orario;
    private String stato;
    private double prezzo;
    private String partenza;
    private String arrivo;

    public Ticket(String id, String tratta, String data, String orario, String stato, double prezzo, String partenza, String arrivo) {
        this.id = id;
        this.tratta = tratta;
        this.data = data;
        this.orario = orario;
        this.stato = stato;
        this.prezzo = prezzo;
        this.partenza = partenza;
        this.arrivo = arrivo;
    }

    public String getTicketId() { return id; }
    public String getTratta() { return tratta; }
    public String getData() { return data; }
    public String getOrario() { return orario; }
    public String getStato() { return stato; }
    public double getPrezzo() { return prezzo; }
    public String getPartenza() { return partenza; }
    public String getArrivo() { return arrivo; }
}
