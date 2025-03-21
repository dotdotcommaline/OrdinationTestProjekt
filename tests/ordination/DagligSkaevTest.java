package ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DagligSkaevTest {
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("121256-0512", "Jane Jensen", 63.4);
    }

    @Test
    void Constructor() {
        LocalDate start = LocalDate.of(2021, 1, 23);
        LocalDate slut = LocalDate.of(2021, 1, 24);
        DagligSkaev ds = new DagligSkaev(start, slut, patient);
        assertEquals(start, ds.getStartDato());
        assertEquals(slut, ds.getSlutDato());
        ArrayList<Dosis> doser = ds.getDoser();
        assertTrue(doser.isEmpty(), "Doser-listen skal initialt være tom");
    }

    @Test
    void OpretDosisValid() {
        DagligSkaev ds = new DagligSkaev(LocalDate.now(), LocalDate.now().plusDays(5), patient);
        ds.opretDosis(LocalTime.of(9, 0), 1.0);
        ArrayList<Dosis> doser = ds.getDoser();
        assertEquals(1, doser.size());
        Dosis d = doser.get(0);
        assertEquals(LocalTime.of(9, 0), d.getTid());
        assertEquals(1.0, d.getAntal(), 0.001);
    }

    @Test
    void OpretDosisMultiple() {
        //her kan tilføjes et vilkårligt antal doser
        DagligSkaev dagligSkaev = new DagligSkaev(LocalDate.now(), LocalDate.now().plusDays(5), patient);
        dagligSkaev.opretDosis(LocalTime.of(9, 0), 1.0);
        dagligSkaev.opretDosis(LocalTime.of(10, 0), 0.5);
        dagligSkaev.opretDosis(LocalTime.of(11, 0), 1.5);
        dagligSkaev.opretDosis(LocalTime.of(12, 0), 2.0);
        dagligSkaev.opretDosis(LocalTime.of(13, 0), 1.0);
        ArrayList<Dosis> doser = dagligSkaev.getDoser();
        assertEquals(5, doser.size(), "Doser-listen skal kunne rumme vilkårligt mange doser");
        //samlet dosis forventes at være 1.0+0.5+1.5+2.0+1.0 = 6.0
        assertEquals(6.0, dagligSkaev.samletDosis(), 0.001);
    }

    @Test
    void SamletDosis() {
        DagligSkaev dagligSkaev = new DagligSkaev(LocalDate.now(), LocalDate.now().plusDays(5), patient);
        dagligSkaev.opretDosis(LocalTime.of(9, 0), 1.0);
        dagligSkaev.opretDosis(LocalTime.of(12, 0), 1.5);
        assertEquals(2.5, dagligSkaev.samletDosis(), 0.001);
    }

    @Test
    void DoegnDosis() {
        //for en test med alle doser givet samme dag: periode = 1 dag.
        DagligSkaev dagligSkaev = new DagligSkaev(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 1), patient);
        dagligSkaev.opretDosis(LocalTime.of(9, 0), 1.0);
        dagligSkaev.opretDosis(LocalTime.of(12, 0), 1.0);
        dagligSkaev.opretDosis(LocalTime.of(15, 0), 1.0);
        //forventet døgndosis = (1.0+1.0+1.0) / 1 = 3.0
        assertEquals(3.0, dagligSkaev.doegnDosis(), 0.001);
    }
}