package pl.ing.pionteching.transaction.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@QuarkusTest
public class AccountTest {
    @Test
    public void testTransactionDebitOperationSimple() {
        Account account = new SimpleAccount("31074318698137062235845814");

        Assertions.assertEquals(account.getAccount(), "31074318698137062235845814");
        Assertions.assertEquals(account.getCreditCount(), 0);
        Assertions.assertEquals(account.getDebitCount(), 0);
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(0)), 0);

        account.debitOperation(BigDecimal.valueOf(10.10));
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(-10.10)), 0);
        Assertions.assertEquals(account.getCreditCount(), 0);
        Assertions.assertEquals(account.getDebitCount(), 1);
    }

    @Test
    public void testTransactionCreditOperationSimple() {
        Account account = new SimpleAccount("31074318698137062235845814");

        Assertions.assertEquals(account.getAccount(), "31074318698137062235845814");
        Assertions.assertEquals(account.getCreditCount(), 0);
        Assertions.assertEquals(account.getDebitCount(), 0);
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(0)), 0);

        account.creditOperation(BigDecimal.valueOf(10.10));
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(10.10)), 0);
        Assertions.assertEquals(account.getCreditCount(), 1);
        Assertions.assertEquals(account.getDebitCount(), 0);
    }

    @Test
    public void testTransactionDebitOperationAtomic() {
        Account account = new AtomicAccount("31074318698137062235845814");

        Assertions.assertEquals(account.getAccount(), "31074318698137062235845814");
        Assertions.assertEquals(account.getCreditCount(), 0);
        Assertions.assertEquals(account.getDebitCount(), 0);
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(0)), 0);

        account.debitOperation(BigDecimal.valueOf(10.10));
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(-10.10)), 0);
        Assertions.assertEquals(account.getCreditCount(), 0);
        Assertions.assertEquals(account.getDebitCount(), 1);
    }

    @Test
    public void testTransactionCreditOperationAtomic() {
        Account account = new AtomicAccount("31074318698137062235845814");

        Assertions.assertEquals(account.getAccount(), "31074318698137062235845814");
        Assertions.assertEquals(account.getCreditCount(), 0);
        Assertions.assertEquals(account.getDebitCount(), 0);
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(0)), 0);

        account.creditOperation(BigDecimal.valueOf(10.10));
        Assertions.assertEquals(account.getBalance().compareTo(BigDecimal.valueOf(10.10)), 0);
        Assertions.assertEquals(account.getCreditCount(), 1);
        Assertions.assertEquals(account.getDebitCount(), 0);
    }

    @Test
    public void testAccountsEquality() {
        Account accountAtomic = new AtomicAccount("31074318698137062235845814");
        Account accountSimple = new SimpleAccount("31074318698137062235845814");

        Assertions.assertEquals(accountSimple, accountAtomic);
        Assertions.assertNotEquals(accountSimple, new Object());
        Assertions.assertNotEquals(accountAtomic, new Object());
        Assertions.assertEquals(accountSimple, accountSimple);
        Assertions.assertEquals(accountSimple.hashCode(), accountAtomic.hashCode());
    }

}
