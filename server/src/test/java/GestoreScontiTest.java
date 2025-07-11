
import com.trenical.grpc.Tratta;
import com.trenical.grpc.OffertaResponse;
import com.trenical.server.model.Utente;
import com.trenical.server.repository.UtenteRepository;
import com.trenical.server.sconto.GestoreSconti;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class GestoreScontiTest {

    private final GestoreSconti gestore = new GestoreSconti();

    @BeforeEach
    void cleanRepository() {
        // Resetta il file utenti.json a una lista vuota
        // (oppure elimina manualmente server/data/utenti.json prima di ogni run)
        // Qui assumiamo che all’avvio non ci siano utenti nel file.
    }

    private Tratta buildTratta(String data, String oraPartenza) {
        return Tratta.newBuilder()
                .setId("TID")
                .setStazionePartenza("A")
                .setStazioneArrivo("B")
                .setData(data)
                .setOrarioPartenza(oraPartenza)
                .setOrarioArrivo(LocalTime.parse(oraPartenza).plusHours(1).toString())
                .setPrezzo(100.0)
                .build();
    }

    @Test
    void testScontoStudente() {
        String userId = "studente1";
        // Utente con email da studente: isStudente() == true
        UtenteRepository.salvaUtente(new Utente(userId, "mario@studenti.unical.it"));

        String domani = LocalDate.now().plusDays(1).toString();
        Tratta tratta = buildTratta(domani, "12:00");

        OffertaResponse off = gestore.calcolaMiglioreOfferta(tratta, userId);
        assertEquals(80.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto Studente", off.getTipo());
        assertEquals("20% di sconto per studenti", off.getDescrizione());
    }

    @Test
    void testScontoFedelta() {
        String userId = "fedel1";
        Utente u = new Utente(userId, "luca@dominio.com");
        u.setFedelta(true);
        UtenteRepository.salvaUtente(u);

        String dopodomani = LocalDate.now().plusDays(2).toString();
        Tratta tratta = buildTratta(dopodomani, "15:30");

        OffertaResponse off = gestore.calcolaMiglioreOfferta(tratta, userId);
        assertEquals(85.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto FedeltàTreno", off.getTipo());
        assertEquals("15% di sconto riservato ai clienti FedeltàTreno", off.getDescrizione());
    }

    @Test
    void testScontoWeekend() {
        String userId = "anonimo";
        UtenteRepository.salvaUtente(new Utente(userId, "anonimo@dominio.com"));

        // Forza il test ad essere valido solo nel weekend
        LocalDate oggi = LocalDate.now();
        if (!(oggi.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || oggi.getDayOfWeek() == java.time.DayOfWeek.SUNDAY)) {
            System.out.println("⏩ Test ignorato: oggi non è weekend (" + oggi.getDayOfWeek() + ")");
            return;
        }

        String dataViaggio = oggi.plusDays(1).toString(); // qualunque giorno successivo
        Tratta tratta = buildTratta(dataViaggio, "09:45");

        OffertaResponse off = gestore.calcolaMiglioreOfferta(tratta, userId);
        assertEquals(90.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto Weekend", off.getTipo());
        assertEquals("10% di sconto se acquisti nel weekend", off.getDescrizione());
    }

    @Test
    void testScontoLastMinute() {
        String userId = "anonimo2";
        UtenteRepository.salvaUtente(new Utente(userId, "user2@dominio.com"));

        String oggi = LocalDate.now().toString();
        // orario 30 minuti da adesso per garantire last minute
        String ora = LocalTime.now().plusMinutes(30).withSecond(0).withNano(0).toString();
        Tratta tratta = buildTratta(oggi, ora);

        OffertaResponse off = gestore.calcolaMiglioreOfferta(tratta, userId);
        assertEquals(75.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto Last Minute", off.getTipo());
        assertEquals("25% di sconto se il treno parte entro 2 ore", off.getDescrizione());
    }
}