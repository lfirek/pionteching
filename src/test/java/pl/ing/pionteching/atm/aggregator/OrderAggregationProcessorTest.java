package pl.ing.pionteching.atm.aggregator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.ing.pionteching.atm.dto.ATM;
import pl.ing.pionteching.atm.dto.RequestType;
import pl.ing.pionteching.atm.dto.Task;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@QuarkusTest
class OrderAggregationProcessorTest {


    OrderAggregationProcessor orderAggregationProcessor = new OrderAggregationProcessor();

    @Test
    public void testOrderCalculatePriority() {

        List<Task> serviceTasks = Arrays.asList(
                new Task(1, 1, RequestType.SIGNAL_LOW),
                new Task(1, 2, RequestType.STANDARD),
                new Task(1, 3, RequestType.PRIORITY),
                new Task(1, 4, RequestType.FAILURE_RESTART));

        List<ATM> orderListSync = orderAggregationProcessor.defaultAggregation(serviceTasks);
        List<ATM> orderListPara = orderAggregationProcessor.parallelAggregation(serviceTasks);

        Arrays.asList(orderListSync, orderListPara).forEach(orderList -> {
            Assertions.assertEquals(orderList.size(), 4);
            Assertions.assertEquals(orderList.get(0).atmId(), 4);
            Assertions.assertEquals(orderList.get(1).atmId(), 3);
            Assertions.assertEquals(orderList.get(2).atmId(), 1);
            Assertions.assertEquals(orderList.get(3).atmId(), 2);
        });

    }

    @Test
    public void testOrderCalculatePriorityWitAtmIdAndRegionGrouping() {

        List<Task> serviceTasks = Arrays.asList(
                new Task(1, 1, RequestType.SIGNAL_LOW),
                new Task(1, 1, RequestType.STANDARD),
                new Task(1, 2, RequestType.PRIORITY),
                new Task(1, 2, RequestType.STANDARD),
                new Task(1, 3, RequestType.PRIORITY),
                new Task(1, 3, RequestType.FAILURE_RESTART),
                new Task(1, 4, RequestType.FAILURE_RESTART),
                new Task(1, 4, RequestType.PRIORITY));

        List<ATM> orderListSync = orderAggregationProcessor.defaultAggregation(serviceTasks);
        List<ATM> orderListPara = orderAggregationProcessor.parallelAggregation(serviceTasks);

        Arrays.asList(orderListSync, orderListPara).forEach(orderList -> {
            Assertions.assertEquals(orderList.size(), 4);
            Assertions.assertEquals(orderList.get(0).atmId(), 3);
            Assertions.assertEquals(orderList.get(1).atmId(), 4);
            Assertions.assertEquals(orderList.get(2).atmId(), 2);
            Assertions.assertEquals(orderList.get(3).atmId(), 1);
        });

    }

    @Test
    public void testOrderCalculateRegionAscSorting() {
        List<Task> serviceTasks = Arrays.asList(
                new Task(2, 1, RequestType.STANDARD),
                new Task(3, 1, RequestType.STANDARD),
                new Task(1, 1, RequestType.STANDARD),
                new Task(4, 1, RequestType.STANDARD));

        List<ATM> orderListSync = orderAggregationProcessor.defaultAggregation(serviceTasks);
        List<ATM> orderListPara = orderAggregationProcessor.parallelAggregation(serviceTasks);

        Arrays.asList(orderListSync, orderListPara).forEach(orderList -> {
            Assertions.assertEquals(orderList.size(), 4);
            Assertions.assertEquals(orderList.get(0).region(), 1);
            Assertions.assertEquals(orderList.get(1).region(), 2);
            Assertions.assertEquals(orderList.get(2).region(), 3);
            Assertions.assertEquals(orderList.get(3).region(), 4);

        });

    }

    @Test
    public void testOrderCalculateSortingWithIncomingOrder() {
        List<Task> serviceTasks = Arrays.asList(
                new Task(3, 2, RequestType.STANDARD),
                new Task(1, 2, RequestType.STANDARD),
                new Task(2, 2, RequestType.STANDARD),
                new Task(3, 1, RequestType.STANDARD),
                new Task(1, 1, RequestType.STANDARD),
                new Task(2, 1, RequestType.STANDARD));

        List<ATM> orderListSync = orderAggregationProcessor.defaultAggregation(serviceTasks);
        List<ATM> orderListPara = orderAggregationProcessor.parallelAggregation(serviceTasks);

        Arrays.asList(orderListSync, orderListPara).forEach(orderList -> {
            Assertions.assertEquals(orderList.size(), 6);
            Assertions.assertEquals(orderList.get(0).region(), 1);
            Assertions.assertEquals(orderList.get(0).atmId(), 2);
            Assertions.assertEquals(orderList.get(1).region(), 1);
            Assertions.assertEquals(orderList.get(1).atmId(), 1);
            Assertions.assertEquals(orderList.get(2).region(), 2);
            Assertions.assertEquals(orderList.get(2).atmId(), 2);
            Assertions.assertEquals(orderList.get(3).region(), 2);
            Assertions.assertEquals(orderList.get(3).atmId(), 1);
            Assertions.assertEquals(orderList.get(4).region(), 3);
            Assertions.assertEquals(orderList.get(4).atmId(), 2);
            Assertions.assertEquals(orderList.get(5).region(), 3);
            Assertions.assertEquals(orderList.get(5).atmId(), 1);
        });


    }

}