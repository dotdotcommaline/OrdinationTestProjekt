package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PN extends Ordination {

    private double antalEnheder;

    private ArrayList<LocalDate> dageGivet = new ArrayList<LocalDate>();

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
    // currentDate -1 kommer an på fast eller skaev og et år frem
    public boolean givDosis(LocalDate givetDato) {
        // TODO
        if (givetDato.isBefore(getStartDato()) || givetDato.isAfter(getSlutDato())) {
            return false;
        }

        dageGivet.add(givetDato);
        return true;
    }

    public double doegnDosis() {
        // TODO
        int antalGange = getAntalGangeGivet();

        LocalDate første = dageGivet.get(0);
        LocalDate sidste = dageGivet.get(0);

        for (int i = 0; i < dageGivet.size(); i++) {
            LocalDate dato = dageGivet.get(i);
            if (dato.isBefore(første)) {
                første = dato;
            }
            if (dato.isAfter(sidste)) {
                sidste = dato;
            }
        }

        long dageMellem = ChronoUnit.DAYS.between(første, sidste) + 1;
        return (antalGange * antalEnheder) / dageMellem;
    }

    @Override
    public String getType() {
        return "PN";
    }


    public double samletDosis() {
        // TODO
        return getAntalGangeGivet() * antalEnheder;
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     *
     * @return
     */
    public int getAntalGangeGivet() {
        // TODO
        return dageGivet.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

}
