package com.wallet_service.domain.model;

import java.util.Objects;

/**
 * A class that describes a user of a banking service through authorization using a login and password.
 */
public class User {

    /**
     * User login.
     */
    private final String login;

    /**
     * User password.
     */
    private final String password;

    /**
     * User's bank account.
     */
    private final BankAccount account;

    /**
     * Creates a user with a login and password and links a bank account.
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        account = new BankAccount(String.valueOf(Objects.hash(login, password)));
    }

    /**
     * Returns login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns bank account.
     */
    public BankAccount getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user.login.equals(login) && user.password.equals(password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
