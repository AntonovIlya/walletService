package com.wallet_service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * A class that describes a user of a banking service through authorization using a login and password.
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    /**
     * User login.
     */
    @ToString.Include
    @EqualsAndHashCode.Include
    private final String login;

    /**
     * User password.
     */
    @EqualsAndHashCode.Include
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
}