package com.trenical.client.factory;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;

public class TicketFactory {

    public static Ticket fromResponse(BigliettoResponse response, String tratta, String data) {
        return new Ticket(
                response.getBigliettoId(),
                tratta,
                data,
                response.getStato()
        );
    }
}
