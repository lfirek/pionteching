package pl.ing.pionteching.transaction.aggregator;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.ing.pionteching.common.Aggregator;
import pl.ing.pionteching.transaction.domain.Account;
import pl.ing.pionteching.transaction.dto.Transaction;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;

@Singleton
public class TransactionsAggregator implements Aggregator<List<Transaction>, List<Account>> {

    private final TransactionsAggregationProcessor aggregationProcessor = new TransactionsAggregationProcessor();

    @ConfigProperty(name = "transactions.parallelAggregationMinListSize")
    int parallelAggregationMinListSize;

    @ConfigProperty(name = "transactions.parallelSortMinListSize")
    int parallelSortMinListSize;

    /**
     * Method aggregates transactions into list of account ordered by account number.
     * This method use configuration properties to tuning performance sorting and aggregation.
     *
     * @param transactions List of transactions in any order
     * @return Sorted list of accounts
     */
    @Override
    public List<Account> aggregate(List<Transaction> transactions) {
        Map<String, Account> accounts = transactions.size() >= parallelAggregationMinListSize ?
                this.aggregationProcessor.parallelAggregation(transactions) :
                this.aggregationProcessor.defaultAggregation(transactions);

        return accounts.size() >= parallelSortMinListSize ?
                this.aggregationProcessor.parallelAccountsSort(accounts) :
                this.aggregationProcessor.defaultAccountsSort(accounts);
    }

}