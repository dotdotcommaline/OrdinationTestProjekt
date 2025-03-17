package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination {
    private ArrayList<Dosis> doser = new ArrayList<Dosis>();


    public DagligSkaev(LocalDate startDato, LocalDate slutDato, Patient patient) {
        super(startDato, slutDato, patient);
    }
    // TODO


    public ArrayList<Dosis> getDoser() {
        return doser;
    }

    // test ækvivalensklasser LocaleTime tid: 05.59 06.00   double antal: -1 25
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

        doser.add(new Dosis(tid, antal));
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
        return "Daglig Skaev";
    }
}
