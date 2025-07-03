package com.trenical.client.factory;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketFactoryTest {

    @Test
    void testFromResponse() {
        BigliettoResponse response = BigliettoResponse.newBuilder()
                .setBigliettoId("B456")
                .setStato("Confermato")
                .setPrezzo(39.99)
                .setOrario("12:00")
                .build();

        Ticket ticket = TicketFactory.fromResponse(response, "Napoli-Bologna", "2025-07-02");

        assertEquals("B456", ticket.getTicketId());
        assertEquals("Napoli-Bologna", ticket.getTratta());
        assertEquals("2025-07-02", ticket.getData());
        assertEquals("12:00", ticket.getOrario());
        assertEquals("Confermato", ticket.getStato());
        assertEquals(39.99, ticket.getPrezzo());
    }
}
