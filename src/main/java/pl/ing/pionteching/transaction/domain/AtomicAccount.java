package pl.ing.pionteching.transaction.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class AtomicAccount extends Account {

    private final String account;
    private final AtomicInteger debitCount, creditCount;
    private final AtomicReference<BigDecimal> balance;

    public AtomicAccount(String account) {
        this.account = account;
        this.debitCount = new AtomicInteger(0);
        this.creditCount = new AtomicInteger(0);
        this.balance = new AtomicReference<>(BigDecimal.ZERO);
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public int getDebitCount() {
        return debitCount.get();
    }

    private void incrementDebitCount() {
        debitCount.incrementAndGet();
    }

    @Override
    public int getCreditCount() {
        return creditCount.get();
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    private void incrementCreditCount() {
        creditCount.incrementAndGet();
    }

    @Override
    public void debitOperation(BigDecimal amount) {
        this.incrementDebitCount();
        this.balance.accumulateAndGet(amount, BigDecimal::subtract);
    }

    @Override
    public void creditOperation(BigDecimal amount) {
        this.incrementCreditCount();
        this.balance.accumulateAndGet(amount, BigDecimal::add);
    }

}