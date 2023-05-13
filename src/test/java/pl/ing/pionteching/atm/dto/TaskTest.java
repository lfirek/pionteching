package pl.ing.pionteching.atm.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TaskTest {

    @Test
    public void testTaskPriority() {
        Task task = new Task(0, 0, RequestType.STANDARD);
        Assertions.assertEquals(task.requestType(), RequestType.STANDARD);
    }

}