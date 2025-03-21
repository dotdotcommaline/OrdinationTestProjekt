package ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PNTest {
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("121256-0512", "Jane Jensen", 63.4);
    }

    @Test
    void Constructor() {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate slut = LocalDate.of(2021, 1, 12);
        PN pn = new PN(start, slut, patient);
        assertEquals(start, pn.getStartDato());
        assertEquals(slut, pn.getSlutDato());
        assertEquals(0, pn.getAntalGangeGivet(), "dageGivet skal være tom ved konstruktion");
    }

    @Test
    void GivDosis_Valid() {
        PN pn = new PN(LocalDate.of(2021,1,1), LocalDate.of(2021,1,12), patient);
        boolean result = pn.givDosis(LocalDate.of(2021, 1, 5));
        assertTrue(result);
        assertEquals(1, pn.getAntalGangeGivet());
    }

    @Test
    void GivDosisInvalidBefore() {
        PN pn = new PN(LocalDate.of(2021,1,1), LocalDate.of(2021,1,12), patient);
        boolean result = pn.givDosis(LocalDate.of(2020, 12, 31));
        assertFalse(result);
        assertEquals(0, pn.getAntalGangeGivet());
    }

    @Test
    void GivDosisInvalidAfter() {
        PN pn = new PN(LocalDate.of(2021,1,1), LocalDate.of(2021,1,12), patient);
        boolean result = pn.givDosis(LocalDate.of(2021, 1, 15));
        assertFalse(result);
        assertEquals(0, pn.getAntalGangeGivet());
    }

    @Test
    void SamletDosis() {
        PN pn = new PN(LocalDate.of(2021,1,1), LocalDate.of(2021,1,12), patient);
        pn.setAntalEnheder(2.0);
        pn.givDosis(LocalDate.of(2021,1,5));
        assertEquals(2.0, pn.samletDosis(), 0.001);
    }

    @Test
    void DoegnDosisSingleDose() {
        PN pn = new PN(LocalDate.of(2021,1,1), LocalDate.of(2021,1,12), patient);
        pn.setAntalEnheder(2.0);
        pn.givDosis(LocalDate.of(2021,1,5));
        //med en dosis perioden = 1 dag -> doegnDosis = 2.0 / 1
        assertEquals(2.0, pn.doegnDosis(), 0.001);
    }

    @Test
    void DoegnDosisMultipleDoses() {
        PN pn = new PN(LocalDate.of(2021,1,1), LocalDate.of(2021,1,12), patient);
        pn.setAntalEnheder(2.0);
        pn.givDosis(LocalDate.of(2021,1,3));
        pn.givDosis(LocalDate.of(2021,1,5));
        pn.givDosis(LocalDate.of(2021,1,7));
        //tidligste = 2021-01-03, seneste = 2021-01-07, periode = 5 dage og samlet dosis = 3*2.0 = 6.0
        assertEquals(6.0 / 5, pn.doegnDosis(), 0.001);
    }
}