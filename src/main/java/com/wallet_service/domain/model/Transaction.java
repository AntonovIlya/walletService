package com.wallet_service.domain.model;

import lombok.*;

/**
 * Class describing a unit of banking transaction.
 */
@ToString
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    /**
     * Transaction ID
     */
    @EqualsAndHashCode.Include
    private final String transactionID;
    /**
     * Value of transaction.
     */
    @Setter
    private double value;
    /**
     * The user performing the transaction.
     */
    @EqualsAndHashCode.Include
    private final User user;

    private String dateTime;
}
