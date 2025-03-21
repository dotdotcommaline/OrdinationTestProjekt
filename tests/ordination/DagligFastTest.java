package ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DagligFastTest {
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("121256-0512", "Jane Jensen", 63.4);
    }

    @Test
    void Constructor() {
        LocalDate start = LocalDate.of(2021, 1, 10);
        LocalDate slut = LocalDate.of(2021, 1, 12);
        DagligFast dagligFast = new DagligFast(start, slut, patient);
        assertEquals(start, dagligFast.getStartDato());
        assertEquals(slut, dagligFast.getSlutDato());
        Dosis[] doser = dagligFast.getDoser();
        assertEquals(4, doser.length);
        for (Dosis d : doser) {
            assertNull(d, "Alle elementer i doser-arrayet skal initialt være null");
        }
    }

    @Test
    void OpretDosis() {
        LocalDate start = LocalDate.of(2021, 1, 10);
        LocalDate slut = LocalDate.of(2021, 1, 12);
        DagligFast dagligFast = new DagligFast(start, slut, patient);
        //tilføj første dosis
        dagligFast.opretDosis(LocalTime.of(8, 0), 2.0);
        Dosis[] doser = dagligFast.getDoser();
        assertNotNull(doser[0]);
        assertEquals(2.0, doser[0].getAntal(), 0.001);
        assertEquals(LocalTime.of(8, 0), doser[0].getTid());
        //tilføj flere doser
        dagligFast.opretDosis(LocalTime.of(12, 0), 1.5);
        dagligFast.opretDosis(LocalTime.of(18, 0), 1.0);
        dagligFast.opretDosis(LocalTime.of(22, 0), 0.5);
        //forsøger at tilføje en ekstra dosis (skal ignoreres)
        dagligFast.opretDosis(LocalTime.of(23, 0), 0.2);
        int count = 0;
        for (Dosis dose : doser) {
            if (dose != null) count++;
        }
        assertEquals(4, count, "Der skal maksimalt være 4 doser");
    }

    @Test
    void SamletDosis() {
        LocalDate startDato = LocalDate.of(2021, 1, 10);
        LocalDate slutDato = LocalDate.of(2021, 1, 12);
        DagligFast dagligFast = new DagligFast(startDato, slutDato, patient);
        dagligFast.opretDosis(LocalTime.of(8, 0), 2.0);
        dagligFast.opretDosis(LocalTime.of(12, 0), 1.5);
        dagligFast.opretDosis(LocalTime.of(18, 0), 1.0);
        dagligFast.opretDosis(LocalTime.of(22, 0), 0.5);
        assertEquals(5.0, dagligFast.samletDosis(), 0.001);
    }

    @Test
    void DoegnDosis() {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate slut = LocalDate.of(2021, 1, 3);
        DagligFast dagligFast = new DagligFast(start, slut, patient);
        dagligFast.opretDosis(LocalTime.of(8, 0), 2.0);
        dagligFast.opretDosis(LocalTime.of(12, 0), 2.0);
        dagligFast.opretDosis(LocalTime.of(18, 0), 2.0);
        // Samlet dosis = 6.0, døgndosis = 6.0 / 3 = 2.0
        assertEquals(2.0, dagligFast.doegnDosis(), 0.001);
    }
}