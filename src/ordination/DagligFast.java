package ordination;

import java.time.LocalDate;
import java.time.LocalTime;

public class DagligFast extends Ordination {
    private Dosis[] doser = new Dosis[4];


    public DagligFast(LocalDate startDato, LocalDate slutDato, Patient patient) {
        super(startDato, slutDato, patient);
    }

    public Dosis[] getDoser() {
        return doser;
    }

    // test ækvivalensklasser LocaleTime tid: 05.59 06.00   double antal: -1 0 2 4 5
    public void opretDosis(LocalTime tid, double antal) {
        // TODO
        int antalNuværendeDosis = 0;

        for (Dosis dosis : doser) {
            antalNuværendeDosis += dosis.getAntal();
        }

        if (antalNuværendeDosis == 4) {
            return;
        } else if (antalNuværendeDosis + antal > 4) {
            return;
        }

        for (int i = 0; i < doser.length; i++) {
            if (doser[i] == null) {
                doser[i] = new Dosis(tid, antal);
                break;
            }
        }
    }

    @Override
    public double samletDosis() {
        double samletDosis = 0;

        for (Dosis dosis : doser) {
            samletDosis += dosis.getAntal();
        }
        return  samletDosis;
    }

    @Override
    public double doegnDosis() {
        return samletDosis() / antalDage();
    }

    @Override
    public String getType() {
        return "Daglig Fast";
    }

    // TODO
}
