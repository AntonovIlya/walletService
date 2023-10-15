package com.wallet_service.domain.repository;

import com.wallet_service.domain.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A wrapper class that simulates a database for storing users.
 */
public class UserRepository {

    /**
     * Data structure for storing users.
     */
    private final Map<Integer, User> users;

    /**
     * Constructs a map implementation. Adding admin as the default user.
     */
    public UserRepository() {
        users = new HashMap<>();
        users.put(Objects.hash("admin", "admin"), new User("admin", "admin"));
    }

    /**
     * Adds a new user.
     *
     * @param user user to be added
     */
    public void userRegistration(User user) {
        users.put(Objects.hash(user.getLogin(), user.getPassword()), user);
    }

    /**
     * Returns User, if non-null, otherwise returns an empty Optional.
     *
     * @param login user login
     * @param password user password
     */
    public Optional<User> getUser(String login, String password) {
        int hashCode = Objects.hash(login, password);
        return Optional.ofNullable(users.get(hashCode));
    }

    /**
     * Returns true if the user with this login and password is admin.
     *
     * @param login user login
     * @param password user password
     */
    public boolean isAdmin(String login, String password) {
        return login.equals("admin") && password.equals("admin");
    }
}
