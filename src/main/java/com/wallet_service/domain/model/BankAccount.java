package com.wallet_service.domain.model;

import com.wallet_service.domain.repository.Log;
import com.wallet_service.domain.repository.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 * A class representing the user's bank account.
 */
public class BankAccount {

    /**
     * Account ID.
     */
    @Setter
    @Getter
    private int id;

    /**
     * Account balance.
     */
    @Setter
    private double balance = 0;

    /**
     * Account transaction history.
     */
    private final Log logBankAccountOperations;

    /**
     * Constructs an account with a specific ID.
     */
    public BankAccount() {
        logBankAccountOperations = new Logger("logOperations");
    }

    /**
     * Returns the current account balance.
     */
    public double getBalance() {
        logBankAccountOperations.log("Account id: " + id + ". Запрос баланса. Текущий баланс: " + balance);
        return balance;
    }

    /**
     * Decreases funds by a certain amount.
     *
     * @param value The amount by which the balance is reduced
     */
    public boolean payment(double value) {
        if (balance > value) {
            logBankAccountOperations.log("Account id: " + id + ". Снятие средств: -" + value);
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
        logBankAccountOperations.log("Account id: " + id + ". Пополнение средств: +" + value);
    }

    /**
     * Returns the transaction history of the account.
     */
    public String getOperationHistory() {
        return logBankAccountOperations.getHistory();
    }
}
