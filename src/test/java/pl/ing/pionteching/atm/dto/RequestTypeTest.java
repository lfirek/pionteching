package pl.ing.pionteching.atm.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RequestTypeTest {
    @Test
    public void testRequestTypePriority() {
        Assertions.assertEquals(RequestType.STANDARD.getPriority(), 0);
        Assertions.assertEquals(RequestType.SIGNAL_LOW.getPriority(), 1);
        Assertions.assertEquals(RequestType.PRIORITY.getPriority(), 2);
        Assertions.assertEquals(RequestType.FAILURE_RESTART.getPriority(), 3);
    }
}