package com.wallet_service.domain.model;

import lombok.*;

/**
 * A class that describes a user of a banking service through authorization using a login and password.
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
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
}
