package com.wallet_service.domain.model;

import com.wallet_service.domain.repository.Log;
import com.wallet_service.domain.repository.Logger;

/**
 * A class representing the user's bank account.
 */
public class BankAccount {

    /**
     * Account ID.
     */
    private final String id;

    /**
     * Account balance.
     */
    private double balance = 0;

    /**
     * Account transaction history.
     */
    private final Log operationsHistory;

    /**
     * Constructs an account with a specific ID.
     */
    public BankAccount(String id) {
        this.id = id;
        operationsHistory = new Logger();
    }

    /**
     * Returns the current account balance.
     */
    public double getBalance() {
        operationsHistory.log("Запрос баланса. Текущий баланс: " + balance);
        return balance;
    }

    /**
     * Decreases funds by a certain amount.
     *
     * @param value The amount by which the balance is reduced
     */
    public boolean payment(double value) {
        if (balance > value) {
            operationsHistory.log("Снятие средств: -" + value);
            balance = balance - value;
            return true;
        }
        return false;
    }

    /**
     * Increases funds by a certain amount.
     *
     * @param value The amount by which the balance increases
     */
    public void refill(double value) {
        balance = balance + value;
        operationsHistory.log("Пополнение средств: +" + value);
    }

    /**
     * Returns the transaction history of the account.
     */
    public String getOperationHistory() {
        return operationsHistory.getHistory();
    }
}
