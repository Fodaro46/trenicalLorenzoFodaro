package com.trenical.client.factory;

import com.trenical.client.factory.DefaultTicketCreator;
import com.trenical.client.factory.TicketCreator;
import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefaultTicketCreatorTest {
    @Test
    void createShouldMapCorrectly() {
        BigliettoResponse resp = BigliettoResponse.newBuilder()
                .setBigliettoId("B123")
                .setStato("Confermato")
                .build();
        TicketCreator creator = new DefaultTicketCreator();
        Ticket t = creator.create(resp, "Roma-Milano", "2025-06-10");
        assertEquals("B123", t.getTicketId());
        assertEquals("Roma-Milano", t.getTratta());
        assertEquals("2025-06-10", t.getData());
        assertEquals("Confermato", t.getStato());
    }
}
