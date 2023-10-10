package com.wallet_service.domain.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    static UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        userRepository = new UserRepository();
    }

    @AfterAll
    static void afterAll() {
        userRepository = null;
    }

    @Test
    void isAdmin() {
        //Arrange
        String login = "admin";
        String password = "admin";

        //Act

        //Assert
        Assertions.assertTrue(userRepository.isAdmin(login, password));
    }
}