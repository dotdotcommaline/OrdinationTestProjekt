package ordination;

import java.time.LocalDate;
import java.time.LocalTime;

public class DagligFast extends Ordination {
    private Dosis[] dosiser = new Dosis[4];


    public DagligFast(LocalDate startDato, LocalDate slutDato, Patient patient) {
        super(startDato, slutDato, patient);
    }

    public Dosis[] getDosiser() {
        return dosiser;
    }

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

        for (int i = 0; i < dosiser.length; i++) {
            if (dosiser[i] == null) {
                dosiser[i] = new Dosis(tid, antal);
                break;
            }
        }
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

    // TODO
}
