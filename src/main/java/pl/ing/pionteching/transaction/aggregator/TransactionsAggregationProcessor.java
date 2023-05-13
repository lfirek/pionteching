package pl.ing.pionteching.transaction.aggregator;

import pl.ing.pionteching.transaction.domain.Account;
import pl.ing.pionteching.transaction.domain.AtomicAccount;
import pl.ing.pionteching.transaction.domain.SimpleAccount;
import pl.ing.pionteching.transaction.dto.Transaction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionsAggregationProcessor {

    private static final Comparator<Account> ACCOUNT_COMPARATOR = Comparator.comparing(Account::getAccount);

    /**
     * Method aggregates transactions into a list of accounts.
     * This method is synchronous with the best performance for small transaction list.
     *
     * @param transactions List of transactions in any order
     * @return Map of accounts with key as account number.
     */
    public Map<String, Account> defaultAggregation(List<Transaction> transactions) {
        Map<String, Account> accounts = new HashMap<>();

        for (Transaction transaction : transactions) {
            accounts.computeIfAbsent(transaction.creditAccount(), SimpleAccount::new).creditOperation(transaction.amount());
            accounts.computeIfAbsent(transaction.debitAccount(), SimpleAccount::new).debitOperation(transaction.amount());
        }

        return accounts;
    }

    /**
     * Method aggregates transactions into a list of accounts.
     * This method uses parallel aggregation with the best performance for large transaction list.
     *
     * @param transactions List of transactions in any order
     * @return Map of accounts with key as account number.
     */
    public Map<String, Account> parallelAggregation(List<Transaction> transactions) {
        Map<String, Account> accounts = new ConcurrentHashMap<>();

        transactions.parallelStream().forEach(transaction -> {
            accounts.computeIfAbsent(transaction.creditAccount(), AtomicAccount::new).creditOperation(transaction.amount());
            accounts.computeIfAbsent(transaction.debitAccount(), AtomicAccount::new).debitOperation(transaction.amount());
        });

        return accounts;
    }

    /**
     * Method sorts accounts in a particular order.
     * Accounts are sorted by account number.
     * The method is synchronous with the best performance for small accounts list.
     *
     * @param accounts Map of accounts with key as account number.
     * @return Sorted list of accounts
     */
    public List<Account> defaultAccountsSort(Map<String, Account> accounts) {
        return accounts.values().stream().sorted(ACCOUNT_COMPARATOR).toList();
    }


    /**
     * Method sorts accounts in a particular order.
     * Accounts are sorted by account number.
     * The method uses parallel stream with the best performance for large account list.
     *
     * @param accounts Map of accounts with key as account number.
     * @return Sorted list of accounts
     */
    public List<Account> parallelAccountsSort(Map<String, Account> accounts) {
        return accounts.values().parallelStream().sorted(ACCOUNT_COMPARATOR).toList();
    }

}
