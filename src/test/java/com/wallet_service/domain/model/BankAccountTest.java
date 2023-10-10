package com.wallet_service.domain.model;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankAccountTest {

    static BankAccount account;

    @BeforeAll
    static void beforeAll() {
        account = new BankAccount("id");
    }

    @AfterAll
    static void afterAll() {
        account = null;
    }

    @Test
    @Order(2)
    void payment() {
        //Arrange
        double paymentValue1 = 500;
        double paymentValue2 = 1500;

        //Act

        //Assert
        Assertions.assertFalse(account.payment(paymentValue2));
        Assertions.assertTrue(account.payment(paymentValue1));
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