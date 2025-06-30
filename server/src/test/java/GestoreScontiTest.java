
import com.trenical.grpc.Tratta;
import com.trenical.grpc.OffertaResponse;
import com.trenical.server.sconto.GestoreSconti;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestoreScontiTest {

    @Test
    void testNessunoScontoApplicato() {
        GestoreSconti g = new GestoreSconti();
        Tratta t = Tratta.newBuilder()
                .setId("T-01")
                .setStazionePartenza("A")
                .setStazioneArrivo("B")
                .setOrarioPartenza("10:00")
                .setOrarioArrivo("11:00")
                .setData("2025-07-02") // mercoledì
                .setPrezzo(100.0)
                .build();

        OffertaResponse r = g.calcolaMiglioreOfferta(t, "user-123");
        assertEquals(100.0, r.getPrezzoScontato(), 0.01);
        assertEquals("Prezzo pieno", r.getDescrizione());
    }

    @Test
    void testScontoWeekendAttivo() {
        GestoreSconti g = new GestoreSconti();
        Tratta t = Tratta.newBuilder()
                .setId("T-02")
                .setStazionePartenza("C")
                .setStazioneArrivo("D")
                .setOrarioPartenza("10:00")
                .setOrarioArrivo("11:00")
                .setData("2025-07-06") // domenica
                .setPrezzo(100.0)
                .build();

        OffertaResponse r = g.calcolaMiglioreOfferta(t, "user-xyz");
        assertTrue(r.getPrezzoScontato() < 100.0);
        assertEquals("ScontoWeekend", r.getTipo());
    }
}
