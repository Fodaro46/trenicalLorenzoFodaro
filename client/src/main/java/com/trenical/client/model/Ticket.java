package com.trenical.client.model;

public class Ticket {
    private final String ticketId;
    private final String tratta;
    private final String data;
    private final String stato;

    public Ticket(String ticketId, String tratta, String data, String stato) {
        this.ticketId = ticketId;
        this.tratta = tratta;
        this.data = data;
        this.stato = stato;
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
}
