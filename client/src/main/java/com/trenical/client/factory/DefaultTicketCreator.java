package com.trenical.client.factory;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;

public class DefaultTicketCreator implements TicketCreator {
    @Override
    public Ticket create(BigliettoResponse resp, String tratta, String data) {
        return new Ticket(
                resp.getBigliettoId(),
                tratta,
                data,
                resp.getStato(),
                resp.getPrezzo()
        );
    }
}
