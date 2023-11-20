package com.wallet_service.application.in.service;

import com.wallet_service.domain.model.BankAccount;
import com.wallet_service.domain.model.User;
import com.wallet_service.domain.repository.Log;
import com.wallet_service.domain.repository.Logger;
import com.wallet_service.domain.repository.UserRepository;

import java.util.Scanner;

/**
 * A class containing the logic for interacting with a banking service through authorization.
 */
public class UserService {

    private final Scanner scanner;

    /**
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * Transaction service.
     */
    private final TransactionService transactionService;

    /**
     * Audit system.
     */
    private final Log logUserActions;

    /**
     * Authorized user.
     */
    private User currentUser;

    public UserService() {
        userRepository = new UserRepository();
        transactionService = new TransactionService();
        logUserActions = new Logger("logUserActions");
        scanner = new Scanner(System.in);
    }

    /**
     * Provides work with the user authorization menu through the console.
     */
    private void signIn() {
        while (true) {
            System.out.println("Введите логин:");
            String login = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();
            if (login.equals("admin") && password.equals("admin")) {
                selectAdminOperation(currentUser);
                return;
            }
            if (!userRepository.isExists(login, password)) {
                System.out.println("Пользователь с таким логином и паролем не найден.\n");
                System.out.println("Выберите номер действия:\n" +
                        "  1. Повторить авторизацию\n" +
                        "  2. Выход");
                String action = scanner.nextLine();
                switch (action) {
                    case ("1") -> {
                        continue;
                    }
                    case ("2") -> {
                        return;
                    }
                    default -> {
                        System.out.println("Неверная команда, попробуйте снова.\n");
                        continue;
                    }
                }
            }
            logUserActions.log("Авторизация пользователя " + login);
            currentUser = userRepository.getUser(login, password);
            selectUserOperation(currentUser);
            break;
        }
    }

    /**
     * Provides a menu for the admin to work through the console.
     *
     * @param currentUser current authorized user
     */
    private void selectAdminOperation(User currentUser) {
        while (true) {
            System.out.println("Выберите номер действия:\n" +
                    "  1. Аудит системы\n" +
                    "  2. Выход");
            String action = scanner.nextLine();
            switch (action) {
                case ("1") -> {
                    logUserActions.log("Просмотр истории администратором");
                    System.out.println("История: ");
                    System.out.println(logUserActions.getHistory());
                }
                case ("2") -> {
                    return;
                }
            }
        }
    }

    /**
     * Provides a menu for the user to work through the console.
     *
     * @param currentUser current authorized user
     */
    private void selectUserOperation(User currentUser) {
        BankAccount account = currentUser.getAccount();
        while (true) {
            System.out.println("Выберите номер действия:\n" +
                    "  1. Запрос баланса\n" +
                    "  2. Снятие средств\n" +
                    "  3. Пополнение средств\n" +
                    "  4. Просмотр истории операций\n" +
                    "  5. Выход");
            String action = scanner.nextLine();
            switch (action) {
                case ("1") -> System.out.println("Баланс: " + account.getBalance());
                case ("2") -> transactionService.paymentTransaction(currentUser);
                case ("3") -> transactionService.refill(currentUser);
                case ("4") -> {
                    System.out.println("История операций: ");
                    System.out.println(account.getOperationHistory());
                }
                case ("5") -> {
                    userRepository.updateUserData(currentUser);
                    logUserActions.log("Действия пользователя " +
                            currentUser.getLogin() + ":\n" +
                            account.getOperationHistory());
                    return;
                }
                default -> System.out.println("Неверная команда, попробуйте снова.\n");
            }
        }
    }

    /**
     * Provides work with the user registration menu through the console.
     */
    public void signUp(String login, String password) {
        if (userRepository.isExists(login, password)) {
            //todo реализовать ошибку регистрации
            throw new RuntimeException();
        }
        User user = new User(login, password);
        userRepository.userRegistration(user);
        logUserActions.log("Регистрация пользователя " + login);
    }
}
