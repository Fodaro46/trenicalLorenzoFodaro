package com.trenical.client.factory;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;
import com.trenical.grpc.TicketResponse;

public class TicketFactory {

    public static Ticket fromResponse(BigliettoResponse response, String tratta, String data) {
        return new Ticket(
                response.getBigliettoId(),
                tratta,
                data,
                response.getStato(),
                response.getPrezzo()
        );
    }

    public static Ticket fromTicketResponse(TicketResponse response) {
        return new Ticket(
                response.getTicketId(),
                response.getTrainNumber(),       // usare come "tratta"
                response.getDepartureTime(),     // usare come "data"
                "Confermato",                    // nessuno stato nel response
                response.getPrezzo()             // AGGIUNTO: prezzo dal server
        );
    }
}
