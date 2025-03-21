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

    // test ækvivalensklasser LocaleTime tid: 00.00 23.59   double antal: -1 20
    public void opretDosis(LocalTime tid, double antal) {
        // TODO
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
