package com.wallet_service.domain.repository;

import com.wallet_service.domain.db.DBConnection;
import com.wallet_service.domain.model.BankAccount;
import com.wallet_service.domain.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for interacting with the database.
 */
public class UserRepository {

    private final Connection connection;

    public UserRepository() {
        connection = DBConnection.connection;
    }

    /**
     * Adds a new user to DB.
     *
     * @param user user to be added
     */
    public void userRegistration(User user) {
        BankAccount userBankAccount = new BankAccount();
        user.setAccount(userBankAccount);
        String createNewUser = "insert into wallet_service.users(login, password) values (?, ?);";
        String getUserID = "select id from wallet_service.users where login=? and password=?;";
        String createNewBankAccount = "insert into wallet_service.bank_accounts(user_id, balance) values(?, 0);";

        try {
            connection.setAutoCommit(false);

            PreparedStatement registrationUser = connection.prepareStatement(createNewUser);
            registrationUser.setString(1, user.getLogin());
            registrationUser.setString(2, user.getPassword());
            registrationUser.executeUpdate();

            PreparedStatement getID = connection.prepareStatement(getUserID);
            getID.setString(1, user.getLogin());
            getID.setString(2, user.getPassword());
            ResultSet resultSet = getID.executeQuery();
            while (resultSet.next()) {
                userBankAccount.setId(resultSet.getInt(1));
            }

            PreparedStatement registrationBankAccount = connection.prepareStatement(createNewBankAccount);
            registrationBankAccount.setInt(1, userBankAccount.getId());
            registrationBankAccount.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Пользователь успешно зарегестрирован.");
        } catch (SQLException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            try {
                connection.rollback();
                System.err.println("Транзакция отменена.");
                connection.setAutoCommit(true);
            } catch (SQLException rollbackException) {
                System.err.println("Ошибка при откате транзакции: " + rollbackException.getMessage());
            }
        }
    }

    /**
     * Checks if the user exists.
     *
     * @param login    user login
     * @param password user password
     */
    public boolean isExists(String login, String password) {
        String isExist = "select exists(select 1 from wallet_service.users where login = ? and password = ?);";
        try {
            PreparedStatement isExistUser = connection.prepareStatement(isExist);
            isExistUser.setString(1, login);
            isExistUser.setString(2, password);
            ResultSet isExistsResult = isExistUser.executeQuery();
            while (isExistsResult.next()) {
                return isExistsResult.getBoolean(1);
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns User from DB.
     *
     * @param login    user login
     * @param password user password
     */
    public User getUser(String login, String password) {
        User user = new User(login, password);
        BankAccount userBankAccount = new BankAccount();
        String getIDs = "select b.id, user_id, balance\n" +
                "from wallet_service.bank_accounts b\n" +
                "         join wallet_service.users u on u.id = b.user_id\n" +
                "where u.login = ?\n" +
                "  and u.password = ?;";
        try {
            PreparedStatement getID = connection.prepareStatement(getIDs);
            getID.setString(1, login);
            getID.setString(2, password);
            ResultSet accountIDResult = getID.executeQuery();
            while (accountIDResult.next()) {
                userBankAccount.setId(accountIDResult.getInt(1));
                user.setId(accountIDResult.getInt(2));
                userBankAccount.setBalance(accountIDResult.getInt(3));
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
        user.setAccount(userBankAccount);
        return user;
    }

    public void updateUserData(User currentUser) {
        String setBankAccountBalance = "update wallet_service.bank_accounts set balance = ? where id = ?;";
        try {
            PreparedStatement setBalance = connection.prepareStatement(setBankAccountBalance);
            setBalance.setDouble(1, currentUser.getAccount().getBalance());
            setBalance.setInt(2, currentUser.getAccount().getId());
            setBalance.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
