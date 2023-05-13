package pl.ing.pionteching.transaction.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class SimpleAccount extends Account {

    private final String account;
    private int debitCount, creditCount;
    private BigDecimal balance;

    public SimpleAccount(String account) {
        this.account = account;
        this.debitCount = 0;
        this.creditCount = 0;
        this.balance = BigDecimal.ZERO;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public int getDebitCount() {
        return debitCount;
    }

    private void incrementDebitCount() {
        this.debitCount += 1;
    }

    @Override
    public int getCreditCount() {
        return creditCount;
    }

    private void incrementCreditCount() {
        this.creditCount += 1;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void debitOperation(BigDecimal amount) {
        this.incrementDebitCount();
        this.balance = balance.subtract(amount);
    }

    @Override
    public void creditOperation(BigDecimal amount) {
        this.incrementCreditCount();
        this.balance = balance.add(amount);
    }

}