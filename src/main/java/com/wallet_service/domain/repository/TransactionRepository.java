package com.wallet_service.domain.repository;

import java.util.HashSet;
import java.util.Set;

/**
 * A wrapper class that simulates a database for storing and checking transaction ID.
 */
public class TransactionRepository {

    /**
     * Data structure for storing transaction ID.
     */
    private final Set<String> transactions;

    /**
     * Constructs a set implementation.
     */
    public TransactionRepository() {
        transactions = new HashSet<>();
    }

    /**
     * Adds a transaction ID to the store.
     *
     * @param transactionID transaction ID
     */
    public void addTransaction(String transactionID) {
        transactions.add(transactionID);
    }

    /**
     * Returns true if the transaction ID is not unique.
     *
     * @param transactionID transaction ID
     */
    public boolean hasTransaction(String transactionID) {
        return transactions.contains(transactionID);
    }
}
