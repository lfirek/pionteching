package pl.ing.pionteching.atm.aggregator;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.ing.pionteching.atm.dto.ATM;
import pl.ing.pionteching.atm.dto.Task;
import pl.ing.pionteching.common.Aggregator;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class OrderAggregator implements Aggregator<List<Task>, List<ATM>> {

    private final OrderAggregationProcessor orderAggregationProcessor = new OrderAggregationProcessor();

    @ConfigProperty(name = "atm.parallelAggregationMinListSize")
    int parallelAggregationMinListSize;

    /**
     * Method aggregates tasks into list of atm ordered by priority and region.
     * This method use configuration properties to tuning performance.
     *
     * @param serviceTasks List of tasks
     * @return Unique ATMs list sorted by priority and region
     */
    public List<ATM> aggregate(List<Task> serviceTasks) {
        return serviceTasks.size() >= parallelAggregationMinListSize ?
                this.orderAggregationProcessor.parallelAggregation(serviceTasks) :
                this.orderAggregationProcessor.defaultAggregation(serviceTasks);
    }

}