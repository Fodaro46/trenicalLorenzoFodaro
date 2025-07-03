package com.trenical.client.factory;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;
import com.trenical.grpc.TicketResponse;

public class TicketFactory {

    public static Ticket fromResponse(BigliettoResponse response, String tratta, String data) {
        String[] tratte = tratta.split("→");
        String partenza = tratte.length > 0 ? tratte[0].trim() : "?";
        String arrivo = tratte.length > 1 ? tratte[1].trim() : "?";

        return new Ticket(
                response.getBigliettoId(),
                tratta,
                data,
                response.getOrario(),
                response.getStato(),
                response.getPrezzo(),
                partenza,
                arrivo
        );
    }

    public static Ticket fromTicketResponse(TicketResponse response) {
        String[] parts = response.getDepartureTime().split("T");
        String data = parts.length > 0 ? parts[0] : "-";
        String orario = parts.length > 1 ? parts[1] : "??:??";

        return new Ticket(
                response.getTicketId(),
                response.getTrainNumber(),
                data,
                orario,
                "Confermato",
                response.getPrezzo(),
                "?", // partenza ignota
                "?"  // arrivo ignoto
        );
    }
}
