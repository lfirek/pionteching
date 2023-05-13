package pl.ing.pionteching.transaction.aggregator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.ing.pionteching.transaction.domain.Account;
import pl.ing.pionteching.transaction.domain.SimpleAccount;
import pl.ing.pionteching.transaction.dto.Transaction;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@QuarkusTest
class TransactionsAggregationProcessorTest {

    TransactionsAggregationProcessor transactionsAggregationProcessor = new TransactionsAggregationProcessor();

    @Test
    public void testAccountsSort() {

        Map<String, Account> accountsMap = new HashMap<String, Account>() {{
            put("00000000000000000000000001", new SimpleAccount("00000000000000000000000001"));
            put("00000000000000000000000002", new SimpleAccount("00000000000000000000000002"));
            put("20000000000000000000000000", new SimpleAccount("20000000000000000000000000"));
            put("10000000000000000000000000", new SimpleAccount("10000000000000000000000000"));
            put("00000000000000000000000000", new SimpleAccount("00000000000000000000000000"));
        }};

        List<Account> accountsSync = transactionsAggregationProcessor.defaultAccountsSort(accountsMap);
        List<Account> accountsPara = transactionsAggregationProcessor.parallelAccountsSort(accountsMap);

        Arrays.asList(accountsSync, accountsPara).forEach(accounts -> {
            Assertions.assertEquals(accounts.size(), 5);
            Assertions.assertEquals(accounts.get(0).getAccount(), "00000000000000000000000000");
            Assertions.assertEquals(accounts.get(1).getAccount(), "00000000000000000000000001");
            Assertions.assertEquals(accounts.get(2).getAccount(), "00000000000000000000000002");
            Assertions.assertEquals(accounts.get(3).getAccount(), "10000000000000000000000000");
            Assertions.assertEquals(accounts.get(4).getAccount(), "20000000000000000000000000");
        });

    }

    @Test
    public void testAccountsOperationPerform() {
        String testAccountNumber0 = "00000000000000000000000000";
        String testAccountNumber1 = "10000000000000000000000000";
        List<Transaction> transactions = Arrays.asList(
                new Transaction(testAccountNumber0, testAccountNumber1, BigDecimal.valueOf(10.10)),
                new Transaction(testAccountNumber1, testAccountNumber0, BigDecimal.valueOf(10)),
                new Transaction(testAccountNumber0, testAccountNumber1, BigDecimal.valueOf(0.90)),
                new Transaction(testAccountNumber0, testAccountNumber1, BigDecimal.valueOf(1)));

        Map<String, Account> accountsSync = transactionsAggregationProcessor.defaultAggregation(transactions);
        Map<String, Account> accountsPara = transactionsAggregationProcessor.parallelAggregation(transactions);

        Arrays.asList(accountsSync, accountsPara).forEach(accounts -> {
            Assertions.assertEquals(accounts.size(), 2);
            Account account0 = accounts.get(testAccountNumber0);
            Account account1 = accounts.get(testAccountNumber1);
            Assertions.assertEquals(account0.getBalance().compareTo(BigDecimal.valueOf(-2)), 0);
            Assertions.assertEquals(account0.getAccount(), testAccountNumber0);
            Assertions.assertEquals(account0.getCreditCount(), 1);
            Assertions.assertEquals(account0.getDebitCount(), 3);
            Assertions.assertEquals(account1.getAccount(), testAccountNumber1);
            Assertions.assertEquals(account1.getBalance().compareTo(BigDecimal.valueOf(2)), 0);
            Assertions.assertEquals(account1.getCreditCount(), 3);
            Assertions.assertEquals(account1.getDebitCount(), 1);
        });

    }


}