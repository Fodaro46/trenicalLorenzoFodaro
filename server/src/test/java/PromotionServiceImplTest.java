
import com.trenical.grpc.Tratta;
import com.trenical.grpc.OffertaResponse;
import com.trenical.server.model.Utente;
import com.trenical.server.repository.UtenteRepository;
import com.trenical.server.service.PromotionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class PromotionServiceImplTest {

    private PromotionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PromotionServiceImpl();
        // Assicuriamoci di avere repository utenti pulito
    }

    private Tratta buildTratta(String data, String oraPartenza) {
        return Tratta.newBuilder()
                .setId("TX")
                .setStazionePartenza("X")
                .setStazioneArrivo("Y")
                .setData(data)
                .setOrarioPartenza(oraPartenza)
                .setOrarioArrivo(LocalTime.parse(oraPartenza).plusHours(2).toString())
                .setPrezzo(100.0)
                .build();
    }

    @Test
    void testGetOffertaStudente() {
        String userId = "studente2";
        UtenteRepository.salvaUtente(new Utente(userId, "anna@studenti.unical.it"));

        String domani = LocalDate.now().plusDays(1).toString();
        Tratta tratta = buildTratta(domani, "14:00");

        OffertaResponse off = service.getOffertaLocally(tratta, userId);
        assertEquals(80.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto Studente", off.getTipo());
    }

    @Test
    void testGetOffertaFedelta() {
        String userId = "fedel2";
        Utente u = new Utente(userId, "peppe@dominio.com");
        u.setFedelta(true);
        UtenteRepository.salvaUtente(u);

        String dopodomani = LocalDate.now().plusDays(2).toString();
        Tratta tratta = buildTratta(dopodomani, "16:00");

        OffertaResponse off = service.getOffertaLocally(tratta, userId);
        assertEquals(85.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto FedeltàTreno", off.getTipo());
    }

    @Test
    void testGetOffertaWeekend() {
        String userId = "anonimo3";
        UtenteRepository.salvaUtente(new Utente(userId, "no@dominio.com"));

        LocalDate oggi = LocalDate.now();
        if (!(oggi.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || oggi.getDayOfWeek() == java.time.DayOfWeek.SUNDAY)) {
            System.out.println("⏩ Test ignorato: oggi non è weekend (" + oggi.getDayOfWeek() + ")");
            return;
        }

        String dataViaggio = oggi.plusDays(3).toString();
        Tratta tratta = buildTratta(dataViaggio, "11:00");

        OffertaResponse off = service.getOffertaLocally(tratta, userId);
        assertEquals(90.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto Weekend", off.getTipo());
    }


    @Test
    void testGetOffertaLastMinute() {
        String userId = "anonimo4";
        UtenteRepository.salvaUtente(new Utente(userId, "test@dominio.com"));

        String oggi = LocalDate.now().toString();
        String ora = LocalTime.now().plusMinutes(20).withSecond(0).withNano(0).toString();
        Tratta tratta = buildTratta(oggi, ora);

        OffertaResponse off = service.getOffertaLocally(tratta, userId);
        assertEquals(75.0, off.getPrezzoScontato(), 0.01);
        assertEquals("Sconto Last Minute", off.getTipo());
    }
}