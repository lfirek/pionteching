package pl.ing.pionteching.atm.aggregator;

import pl.ing.pionteching.atm.dto.ATM;
import pl.ing.pionteching.atm.dto.Task;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class OrderAggregationProcessor {

    private static final Comparator<Task> TASK_PRIORITY_COMPARATOR = Comparator.comparing(Task::priority).reversed();
    private static final Comparator<Task> TASK_COMPARATOR = Comparator.comparing(Task::region)
            .thenComparing(TASK_PRIORITY_COMPARATOR);


    /**
     * Method aggregates tasks to a unique list of ATMs in a specific order.
     * Tasks are sorted by region and priority, higher priority is better.
     * This method is synchronous with the best performance for small task list.
     *
     * @param serviceTasks List of tasks
     * @return Unique ATMs list sorted by priority and region
     */
    public List<ATM> defaultAggregation(List<Task> serviceTasks) {
        return serviceTasks.stream().sorted(TASK_COMPARATOR).map(t -> new ATM(t.region(), t.atmId())).distinct().toList();
    }

    /**
     * Method aggregates tasks to a unique list of ATMs in a specific order.
     * Tasks are sorted by region and priority.
     * This method uses parallel aggregation with the best performance for large task list.
     *
     * @param serviceTasks List of tasks
     * @return Unique ATMs list sorted by priority and region
     */
    public List<ATM> parallelAggregation(List<Task> serviceTasks) {
        Map<Integer, List<Task>> regionMap = new HashMap<>();
        for (Task task : serviceTasks) {
            regionMap.computeIfAbsent(task.region(), k -> new ArrayList<>()).add(task);
        }

        ConcurrentSkipListMap<Integer, List<ATM>> atmsByRegion = new ConcurrentSkipListMap<>();
        regionMap.entrySet().parallelStream().forEach(entrySet -> {
            List<ATM> regionAtms = entrySet.getValue().stream()
                    .sorted(TASK_PRIORITY_COMPARATOR).map(t -> new ATM(t.region(), t.atmId()))
                    .distinct().toList();
            atmsByRegion.put(entrySet.getKey(), regionAtms);
        });

        return atmsByRegion.values().stream().flatMap(Collection::stream).toList();
    }

}
