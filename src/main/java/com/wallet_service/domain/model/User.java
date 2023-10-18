package com.wallet_service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A class that describes a user of a banking service through authorization using a login and password.
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Getter
    @Setter
    private int id;
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
    @Setter
    private BankAccount account;

    /**
     * Creates a user with a login and password and links a bank account.
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
