package com.trenical.server.sconto;

import com.trenical.grpc.Tratta;
import com.trenical.server.model.Utente;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ContestoSconto {

    private final Tratta tratta;
    private final Utente utente;
    private final LocalDate dataViaggio;
    private final LocalTime orarioPartenza;
    private final LocalDateTime dateTimePartenza;
    private final LocalDate dataAcquisto;
    private final LocalDateTime now;

    public ContestoSconto(Tratta tratta, Utente utente) {
        this.tratta = tratta;
        this.utente = utente;
        System.out.println("[CTX] ▶️ Tratta ricevuta:");
        System.out.println("       • id: " + tratta.getId());
        System.out.println("       • partenza: " + tratta.getStazionePartenza());
        System.out.println("       • arrivo: " + tratta.getStazioneArrivo());
        System.out.println("       • data: '" + tratta.getData() + "'");
        System.out.println("       • orario: '" + tratta.getOrarioPartenza() + "'");
        try {
            this.dataViaggio = tratta.getData() != null && !tratta.getData().isBlank()
                    ? LocalDate.parse(tratta.getData())
                    : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data non valida: '" + tratta.getData() + "'");
        }

        try {
            this.orarioPartenza = tratta.getOrarioPartenza() != null && !tratta.getOrarioPartenza().isBlank()
                    ? LocalTime.parse(tratta.getOrarioPartenza())
                    : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Orario non valido: '" + tratta.getOrarioPartenza() + "'");
        }

        if (dataViaggio == null || orarioPartenza == null)
            throw new IllegalArgumentException("Data o orario mancanti nella tratta");

        this.dateTimePartenza = LocalDateTime.of(dataViaggio, orarioPartenza);
        this.now = LocalDateTime.now();
        this.dataAcquisto = now.toLocalDate();
    }

    public Tratta getTratta() {
        return tratta;
    }

    public Utente getUtente() {
        return utente;
    }

    public LocalDate getDataViaggio() {
        return dataViaggio;
    }

    public LocalTime getOrarioPartenza() {
        return orarioPartenza;
    }

    public LocalDateTime getDateTimePartenza() {
        return dateTimePartenza;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public LocalDate getDataAcquisto() {
        return dataAcquisto;
    }
}
