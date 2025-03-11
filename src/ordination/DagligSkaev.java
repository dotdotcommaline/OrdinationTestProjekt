package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination {
    private ArrayList<Dosis> dosiser = new ArrayList<Dosis>();


    public DagligSkaev(LocalDate startDato, LocalDate slutDato, Patient patient) {
        super(startDato, slutDato, patient);
    }
    // TODO


    public void opretDosis(LocalTime tid, double antal) {
        // TODO
        int antalNuværendeDosis = 0;

        for (Dosis dosis : dosiser) {
            antalNuværendeDosis += dosis.getAntal();
        }

        if (antalNuværendeDosis == 4) {
            return;
        } else if (antalNuværendeDosis + antal > 4) {
            return;
        }

        dosiser.add(new Dosis(tid, antal));
    }

    @Override
    public double samletDosis() {
        return 0;
    }

    @Override
    public double doegnDosis() {
        return 0;
    }

    @Override
    public String getType() {
        return "";
    }
}
