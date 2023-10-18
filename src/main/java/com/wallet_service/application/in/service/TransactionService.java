package com.wallet_service.application.in.service;

import com.wallet_service.domain.model.BankAccount;
import com.wallet_service.domain.model.Transaction;
import com.wallet_service.domain.model.User;
import com.wallet_service.domain.repository.TransactionRepository;

import java.util.Scanner;

/**
 * Service for working with users' banking transactions.
 */
public class TransactionService {

    private final Scanner scanner;

    /**
     * Transaction system.
     */
    private final TransactionRepository transactionRepository;

    public TransactionService() {
        transactionRepository = new TransactionRepository();
        scanner = new Scanner(System.in);
    }

    /**
     * Checking the transaction ID and performing a payment operation.
     *
     * @param user current authorized user
     */
    public void paymentTransaction(User user) {
        BankAccount account = user.getAccount();
        System.out.println("Введите идентификатор транзакции:");
        String paymentTransactionID = scanner.nextLine();
        Transaction transaction = new Transaction(paymentTransactionID, user);
        if (!transactionRepository.hasTransaction(transaction)) {
            System.out.println("Введите сумму:");
            String paymentValue = scanner.nextLine();
            double result;
            try {
                result = Double.parseDouble(paymentValue);
            } catch (NumberFormatException | NullPointerException exception) {
                System.out.println("Ошибка. Неверный формат.");
                return;
            }
            if (!account.payment(result)) {
                System.out.println("Ошибка. Недостаточно средств.");
            } else {
                transaction.setValue(result);
                transactionRepository.addTransaction(transaction, "payment");
            }
        } else {
            System.out.println("Ошибка. Идентификатор не уникален.");
        }
    }

    /**
     * Checking the transaction ID and performing a refill operation
     *
     * @param user current authorized user
     */
    public void refill(User user) {
        BankAccount account = user.getAccount();
        System.out.println("Введите идентификатор транзакции:");
        String refillTransactionID = scanner.nextLine();
        Transaction transaction = new Transaction(refillTransactionID, user);
        if (!transactionRepository.hasTransaction(transaction)) {
            System.out.println("Введите сумму:");
            String refillValue = scanner.nextLine();
            double result;
            try {
                result = Double.parseDouble(refillValue);
            } catch (NumberFormatException | NullPointerException exception) {
                System.out.println("Ошибка. Неверный формат");
                return;
            }
            account.refill(result);
            transaction.setValue(result);
            transactionRepository.addTransaction(transaction, "refill");
        } else {
            System.out.println("Ошибка. Идентификатор не уникален.");
        }
    }
}
