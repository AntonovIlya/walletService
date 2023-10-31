package com.wallet_service.domain.model;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankAccountTest {

    static BankAccount account;

    @BeforeAll
    static void beforeAll() {
        account = new BankAccount();
    }

    @AfterAll
    static void afterAll() {
        account = null;
    }

    @Test
    @Order(3)
    void paymentPositive() {
        //Arrange
        double paymentValue = 500;

        //Act

        //Assert
        Assertions.assertTrue(account.payment(paymentValue));
    }

    @Test
    @Order(2)
    void paymentNegative() {
        //Arrange
        double paymentValue = 1500;

        //Act

        //Assert
        Assertions.assertFalse(account.payment(paymentValue));
    }

    @Test
    @Order(1)
    void refill() {
        //Arrange
        double expectedBalance = 1000;
        double refillValue = 1000;

        //Act
        account.refill(refillValue);

        //Assert
        Assertions.assertEquals(expectedBalance, account.getBalance());
    }
}