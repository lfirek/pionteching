package pl.ing.pionteching.transaction.aggregator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.ing.pionteching.transaction.domain.Account;
import pl.ing.pionteching.transaction.dto.Transaction;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@QuarkusTest
public class TransactionsAggregatorTest {

    @Inject
    TransactionsAggregator transactionsAggregator;

    @Test
    public void testAggregationConfigurationProperties() {

        String testAccountNumber0 = "00000000000000000000000000";
        String testAccountNumber1 = "10000000000000000000000000";
        List<Transaction> transactions = Arrays.asList(
                new Transaction(testAccountNumber0, testAccountNumber1, BigDecimal.valueOf(10.10)),
                new Transaction(testAccountNumber1, testAccountNumber0, BigDecimal.valueOf(10)),
                new Transaction(testAccountNumber0, testAccountNumber1, BigDecimal.valueOf(0.90)),
                new Transaction(testAccountNumber0, testAccountNumber1, BigDecimal.valueOf(1)));

        this.transactionsAggregator.parallelSortMinListSize = 0;
        this.transactionsAggregator.parallelAggregationMinListSize = 0;

        List<Account> accountsPSortPAgg = transactionsAggregator.aggregate(transactions);

        this.transactionsAggregator.parallelSortMinListSize = 3;
        this.transactionsAggregator.parallelAggregationMinListSize = 0;

        List<Account> accountsDSortPAgg = transactionsAggregator.aggregate(transactions);

        this.transactionsAggregator.parallelSortMinListSize = 0;
        this.transactionsAggregator.parallelAggregationMinListSize = 5;

        List<Account> accountsPSortDAgg = transactionsAggregator.aggregate(transactions);

        this.transactionsAggregator.parallelSortMinListSize = 3;
        this.transactionsAggregator.parallelAggregationMinListSize = 5;

        List<Account> accountDSortDAgg = transactionsAggregator.aggregate(transactions);

        Assertions.assertEquals(accountsDSortPAgg, accountDSortDAgg);
        Assertions.assertEquals(accountsPSortDAgg, accountDSortDAgg);
        Assertions.assertEquals(accountsPSortPAgg, accountDSortDAgg);
    }

}
