package com.trenical.client.factory;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketFactoryTest {

    @Test
    void testFromResponse() {
        BigliettoResponse response = BigliettoResponse.newBuilder()
                .setBigliettoId("B123")
                .setStato("Confermato")
                .build();

        Ticket ticket = TicketFactory.fromResponse(response, "Roma-Milano", "2025-06-10");

        assertEquals("B123", ticket.getTicketId());
        assertEquals("Roma-Milano", ticket.getTratta());
        assertEquals("2025-06-10", ticket.getData());
        assertEquals("Confermato", ticket.getStato());
    }
}
