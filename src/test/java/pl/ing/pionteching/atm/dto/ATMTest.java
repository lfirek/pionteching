package pl.ing.pionteching.atm.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ATMTest {

    @Test
    public void testATMEquality() {
        ATM atm1 = new ATM(1, 1);
        ATM atm2 = new ATM(1, 1);

        ATM atm3 = new ATM(1, 2);

        ATM atm4 = new ATM(2, 1);

        Assertions.assertEquals(atm1, atm2);
        Assertions.assertEquals(atm2, atm1);
        Assertions.assertEquals(atm1, atm1);

        Assertions.assertNotEquals(atm1, atm3);
        Assertions.assertNotEquals(atm3, atm4);
        Assertions.assertNotEquals(atm4, atm1);
        Assertions.assertNotEquals(atm4, atm2);

        Assertions.assertNotEquals(atm4, "");
    }
}
