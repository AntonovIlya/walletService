package com.wallet_service.domain.repository;

import com.wallet_service.domain.model.Transaction;

import java.util.HashSet;
import java.util.Set;

/**
 * A wrapper class that simulates a database for storing and checking transaction ID.
 */
public class TransactionRepository {

    /**
     * Data structure for storing transaction ID.
     */
    private final Set<Transaction> transactions;

    /**
     * Constructs a set implementation.
     */
    public TransactionRepository() {
        transactions = new HashSet<>();
    }

    /**
     * Adds a transaction ID to the store.
     *
     * @param transaction transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Returns true if the transaction ID is not unique.
     *
     * @param transaction transaction
     */
    public boolean hasTransaction(Transaction transaction) {
        return transactions.contains(transaction);
    }
}
