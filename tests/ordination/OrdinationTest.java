package ordination;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrdinationTest {

    @Test
    void Constructor() {
         Patient patient = new Patient("121256-0512", "Jane Jensen", 63.4);
        LocalDate start = LocalDate.of(2021, 3, 1);
        LocalDate slut = LocalDate.of(2021, 3, 10);
        Ordination ordination = new Ordination(start, slut, patient);
        //test at objektet oprettes med korrekte datoer og tilføjes til patientens ordinationsliste
        assertEquals(start, ordination.getStartDato());
        assertEquals(slut, ordination.getSlutDato());
        assertTrue(patient.getOrdinationer().contains(ordination));
    }

}