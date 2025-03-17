package ordination;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
class DagligFastTest {

    private DagligFast dagligFast;

    @Test
    void opretDosis() {
        //arrange
        LocalTime localTime = LocalTime.of(5,59);
        double antal = -1;

        //act
        double actual = dagligFast.opretDosis(localTime, antal);

        //assert

    }
}