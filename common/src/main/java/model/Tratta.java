package model;

public class Tratta {
    private String id;
    private String stazionePartenza;
    private String stazioneArrivo;
    private String orarioPartenza;
    private String orarioArrivo;
    private double prezzo;

    public Tratta(String id, String stazionePartenza, String stazioneArrivo, String orarioPartenza, String orarioArrivo, double prezzo) {
        this.id = id;
        this.stazionePartenza = stazionePartenza;
        this.stazioneArrivo = stazioneArrivo;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.prezzo = prezzo;
    }

    // Getter e setter

    public String getId() {
        return id;
    }

    public String getStazionePartenza() {
        return stazionePartenza;
    }

    public String getStazioneArrivo() {
        return stazioneArrivo;
    }

    public String getOrarioPartenza() {
        return orarioPartenza;
    }

    public String getOrarioArrivo() {
        return orarioArrivo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStazionePartenza(String stazionePartenza) {
        this.stazionePartenza = stazionePartenza;
    }

    public void setStazioneArrivo(String stazioneArrivo) {
        this.stazioneArrivo = stazioneArrivo;
    }

    public void setOrarioPartenza(String orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public void setOrarioArrivo(String orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return stazionePartenza + " → " + stazioneArrivo + " | " + orarioPartenza + " - " + orarioArrivo + " | €" + prezzo;
    }
}
