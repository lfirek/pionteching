package pl.ing.pionteching.transaction.domain;

import java.math.BigDecimal;
import java.util.Objects;

public sealed abstract class Account permits AtomicAccount, SimpleAccount {

    public abstract String getAccount();

    public abstract int getDebitCount();

    public abstract int getCreditCount();

    public abstract BigDecimal getBalance();

    public abstract void debitOperation(BigDecimal amount);

    public abstract void creditOperation(BigDecimal amount);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Account that) return this.getAccount().equals(that.getAccount());
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getAccount());
    }

}