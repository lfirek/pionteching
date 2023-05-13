package pl.ing.pionteching.atm.resource;

import io.smallrye.common.annotation.NonBlocking;
import pl.ing.pionteching.atm.aggregator.OrderAggregator;
import pl.ing.pionteching.atm.dto.ATM;
import pl.ing.pionteching.atm.dto.Task;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("atms")
public class AtmResource {
    @Inject
    OrderAggregator orderAggregator;

    @Path("calculateOrder")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NonBlocking
    public List<ATM> calculateOrder(List<Task> serviceTasks) {
        return orderAggregator.aggregate(serviceTasks);
    }

}