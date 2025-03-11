package ordination;

import java.time.LocalDate;

public class PN extends Ordination {

    private double antalEnheder;

    public PN(LocalDate startDato, LocalDate slutDato, Patient patient) {
        super(startDato, slutDato, patient);
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givetDato
     * Returnerer true hvis givetDato er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givetDato ignoreres
     *
     * @param givetDato
     * @return
     */
    public boolean givDosis(LocalDate givetDato) {
        // TODO
        return false;
    }

    public double doegnDosis() {
        // TODO
        return 0.0;
    }

    @Override
    public String getType() {
        return "";
    }


    public double samletDosis() {
        // TODO
        return 0.0;
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     *
     * @return
     */
    public int getAntalGangeGivet() {
        // TODO
        return -1;
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

}
