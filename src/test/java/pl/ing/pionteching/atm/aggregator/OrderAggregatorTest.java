package pl.ing.pionteching.atm.aggregator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.ing.pionteching.atm.dto.ATM;
import pl.ing.pionteching.atm.dto.RequestType;
import pl.ing.pionteching.atm.dto.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@QuarkusTest
public class OrderAggregatorTest {

    @Inject
    OrderAggregator orderAggregator;


    @Test
    public void testAggregationConfigurationProperties() {

        List<Task> serviceTasks = Arrays.asList(
                new Task(1, 1, RequestType.SIGNAL_LOW),
                new Task(1, 2, RequestType.STANDARD),
                new Task(1, 3, RequestType.PRIORITY),
                new Task(1, 4, RequestType.FAILURE_RESTART));

        orderAggregator.parallelAggregationMinListSize = 0;
        List<ATM> aggregatePAgg = orderAggregator.aggregate(serviceTasks);

        orderAggregator.parallelAggregationMinListSize = 5;
        List<ATM> aggregateDAgg = orderAggregator.aggregate(serviceTasks);

        Assertions.assertEquals(aggregatePAgg, aggregateDAgg);
    }


}