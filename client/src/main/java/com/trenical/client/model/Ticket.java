package com.trenical.client.model;

public class Ticket {
    private final String ticketId;
    private final String tratta;
    private final String data;
    private final String stato;
    private final double prezzo;

    public Ticket(String ticketId, String tratta, String data, String stato, double prezzo) {
        this.ticketId = ticketId;
        this.tratta = tratta;
        this.data = data;
        this.stato = stato;
        this.prezzo = prezzo;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getTratta() {
        return tratta;
    }

    public String getData() {
        return data;
    }

    public String getStato() {
        return stato;
    }
    public double getPrezzo() {
        return prezzo;
    }
}
