package pl.ing.pionteching.transaction.resource;

import io.smallrye.common.annotation.NonBlocking;
import pl.ing.pionteching.transaction.aggregator.TransactionsAggregator;
import pl.ing.pionteching.transaction.domain.Account;
import pl.ing.pionteching.transaction.dto.Transaction;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("transactions")
public class TransactionResource {
    @Inject
    TransactionsAggregator transactionsAggregator;

    @Path("report")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NonBlocking
    public List<Account> report(List<Transaction> transactions) {
        return transactionsAggregator.aggregate(transactions);
    }
}