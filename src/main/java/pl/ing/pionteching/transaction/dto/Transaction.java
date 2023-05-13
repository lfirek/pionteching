package pl.ing.pionteching.transaction.dto;

import java.math.BigDecimal;

public record Transaction(String debitAccount, String creditAccount, BigDecimal amount) {
}