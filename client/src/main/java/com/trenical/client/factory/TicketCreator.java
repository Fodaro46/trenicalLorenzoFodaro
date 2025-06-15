package com.trenical.client.factory;

import com.trenical.client.model.Ticket;
import com.trenical.grpc.BigliettoResponse;

public interface TicketCreator {
    Ticket create(BigliettoResponse resp, String tratta, String data);
}