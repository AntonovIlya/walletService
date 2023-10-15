package com.wallet_service.application.in.service;

import com.wallet_service.domain.model.BankAccount;
import com.wallet_service.domain.model.User;
import com.wallet_service.domain.repository.Log;
import com.wallet_service.domain.repository.Logger;
import com.wallet_service.domain.repository.UserRepository;

import java.util.Optional;
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
    private final Log usersActionsRepository;

    /**
     * Authorized user.
     */
    private User currentUser;

    public UserService() {
        userRepository = new UserRepository();
        transactionService = new TransactionService();
        usersActionsRepository = new Logger();
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the main operating menu in the console.
     */
    public void start() {
        while (true) {
            System.out.println("Выберите номер действия:\n" +
                    "  1. Регистрация\n" +
                    "  2. Авторизация\n" +
                    "  3. Выход");
            String action = scanner.nextLine();
            switch (action) {
                case ("1") -> signUp();
                case ("2") -> signIn();
                case ("3") -> {
                    System.out.println("Работа программы завершена.");
                    return;
                }
                default -> System.out.println("Неверная команда, попробуйте снова.\n");
            }
        }
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
            Optional<User> user = userRepository.getUser(login, password);
            if (user.isEmpty()) {
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
            usersActionsRepository.log("Авторизация пользователя " + login);
            currentUser = user.get();
            if (userRepository.isAdmin(login, password)) {
                selectAdminOperation(currentUser);
            } else {
                selectUserOperation(currentUser);
            }
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
                    usersActionsRepository.log("Просмотр истории администратором");
                    System.out.println("История: ");
                    System.out.println(usersActionsRepository.getHistory());
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
                    usersActionsRepository.log("Действия пользователя " +
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
    private void signUp() {
        while (true) {
            System.out.println("Введите логин:");
            String login = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();
            System.out.println("Подтвердить ввод?\n");
            System.out.println("Выберите номер действия:\n" +
                    "  1. Повторить регистрацию.\n" +
                    "  2. Зарегистрировать пользователя с таким логином.");
            String action = scanner.nextLine();
            switch (action) {
                case ("1"):
                    break;
                case ("2"):
                    userRepository.userRegistration(new User(login, password));
                    usersActionsRepository.log("Регистрация пользователя " + login);
                    return;
                default:
                    System.out.println("Неверная команда, попробуйте снова.\n");
            }
        }
    }
}
