package controller;

import ordination.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private Patient patient;
    private Laegemiddel laegemiddel1; //paracetamol
    private Laegemiddel laegemiddel2; //Acetylsalicylsyre

    @BeforeEach
    void setUp() {
        controller = Controller.getTestController();
        patient = new Patient("121256-0512", "Jane Jensen", 63.4);
        laegemiddel1 = new Laegemiddel("Paracetamol", 1.0, 1.5, 2.0, "Ml");
        laegemiddel2 = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
    }

    @Test
    void OpretPNOrdinationValidPeriod() {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate slut = LocalDate.of(2021, 1, 12);
        PN pn = controller.opretPNOrdination(start, slut, patient, laegemiddel1, 123);
        assertNotNull(pn, "PN-ordination skal oprettes ved gyldig periode");
        assertEquals(123, pn.getAntalEnheder(), 0.001);
        //tjek at ordinationen er tilføjet til patientens liste
        assertTrue(patient.getOrdinationer().contains(pn));
    }

    @Test
    void OpretPNOrdinationSameDate() {
        LocalDate start = LocalDate.of(2021, 1, 10);
        LocalDate slut = LocalDate.of(2021, 1, 10);
        PN pn = controller.opretPNOrdination(start, slut, patient, laegemiddel1, 1);
        assertNotNull(pn, "PN-ordination skal returnere datoen, hvis datoerne er ens");
    }

    @Test
    void OpretPNOrdinationStartAfterEnd() {
        LocalDate start = LocalDate.of(2021, 1, 20);
        LocalDate slut = LocalDate.of(2021, 1, 10);
        PN pn = controller.opretPNOrdination(start, slut, patient, laegemiddel1, 1);
        assertNull(pn, "PN-ordination skal returnere null, hvis startdato er efter slutdato");
    }

    @Test
    void OpretDagligFastOrdinationValid() {
        LocalDate start = LocalDate.of(2021, 1, 10);
        LocalDate slut = LocalDate.of(2021, 1, 12);
        DagligFast dagligFast = controller.opretDagligFastOrdination(start, slut, patient, laegemiddel1,
                2, 0, 1, 0);
        assertNotNull(dagligFast, "DagligFast-ordination skal oprettes ved gyldig periode");
        //tjek at de 4 doser er oprettet
        assertNotNull(dagligFast.getDoser()[0]);
        assertNotNull(dagligFast.getDoser()[1]);
        assertNotNull(dagligFast.getDoser()[2]);
        assertNotNull(dagligFast.getDoser()[3]);
        //tjek tidspunkterne for doserne (fastsat i Controller)
        assertEquals(LocalTime.of(8, 0), dagligFast.getDoser()[0].getTid());
        assertEquals(LocalTime.of(12, 0), dagligFast.getDoser()[1].getTid());
        assertEquals(LocalTime.of(18, 0), dagligFast.getDoser()[2].getTid());
        assertEquals(LocalTime.of(22, 0), dagligFast.getDoser()[3].getTid());
    }

    @Test
    void OpretDagligSkaevOrdinationValid() {
        LocalDate startDato = LocalDate.of(2021, 1, 23);
        LocalDate slutDato = LocalDate.of(2021, 1, 24);
        LocalTime[] klokkeslæt = {LocalTime.of(12, 0), LocalTime.of(12, 40), LocalTime.of(16, 0), LocalTime.of(18, 45)};
        double[] antalEnheder = {0.5, 1, 2.5, 3};
        DagligSkaev dagligSkaev = controller.opretDagligSkaevOrdination(startDato, slutDato, patient, laegemiddel2, klokkeslæt, antalEnheder);
        assertNotNull(dagligSkaev, "DagligSkaev-ordination skal oprettes ved gyldig input");
        assertEquals(klokkeslæt.length, dagligSkaev.getDoser().size(), "Antal doser skal svare til længden af input arrays");
    }

    @Test
    void OrdinationPNAnvendt() {
        //opret en PN-ordination og anvend en dosis
        LocalDate startDato = LocalDate.of(2021, 1, 1);
        LocalDate slutDato = LocalDate.of(2021, 1, 12);
        PN pn = controller.opretPNOrdination(startDato, slutDato, patient, laegemiddel1, 123);
        assertNotNull(pn);
        //før anvendelse skal antal givne doser være 0
        assertEquals(0, pn.getAntalGangeGivet());
        //anvend ordinationen
        controller.ordinationPNAnvendt(pn, LocalDate.of(2021, 1, 5));
        assertEquals(1, pn.getAntalGangeGivet(), "Antal givet dosis skal opdateres efter anvendelse");
    }

    @Test
    void AnbefaletDosisPrDoegn() {
        double forventet = 63 * 1.5;
        double resultat = controller.anbefaletDosisPrDoegn(patient, laegemiddel1);
        assertEquals(forventet, resultat, 0.001);
    }

    @Test
    void AntalOrdinationerPrVaegtPrLaegemiddel() {
        Patient patient1 = controller.opretPatient("070985-1153", "Finn Madsen", 83.2);
        Patient patient2 = controller.opretPatient("050972-1233", "Hans Jørgensen", 89.4);
        //opret PN-ordinationer med laegemiddel1 (Paracetamol)
        controller.opretPNOrdination(LocalDate.of(2021,1,1), LocalDate.of(2021,1,12), patient1, laegemiddel1, 50);
        controller.opretPNOrdination(LocalDate.of(2021,1,5), LocalDate.of(2021,1,15), patient2, laegemiddel1, 50);
        int antal = controller.antalOrdinationerPrVaegtPrLaegemiddel(80.0, 90.0, laegemiddel1);
        //begge patienter har vægt indenfor intervallet 80-90 (patient1=83.2, patient2=89.4) – derfor forventes 2 ordinationer.
        assertEquals(2, antal);
    }
}