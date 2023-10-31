package com.wallet_service.domain.repository;

import com.wallet_service.domain.db.DBConnection;
import com.wallet_service.domain.model.Transaction;

import java.sql.*;

/**
 * Class for interacting with the database.
 */
public class TransactionRepository {

    private final Connection connection;

    /**
     * Constructs a set implementation.
     */
    public TransactionRepository() {
        connection = DBConnection.connection;
    }

    /**
     * Adds a transaction ID to the store.
     *
     * @param transaction transaction
     */
    public void addTransaction(Transaction transaction, String typeOperation) {
        String setTransaction = "insert into wallet_service.transactions(id, user_id, payment, refill, date_time) values(?, ?, ?, ?, ?);";
        try {
            PreparedStatement set = connection.prepareStatement(setTransaction);
            set.setString(1, transaction.getTransactionID());
            set.setInt(2, transaction.getUser().getId());
            if (typeOperation.equals("payment")) {
                set.setDouble(3, transaction.getValue());
                set.setDouble(4, 0);
            } else {
                set.setDouble(3, 0);
                set.setDouble(4, transaction.getValue());
            }
            set.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            set.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }

    /**
     * Checks if the transaction exists.
     *
     * @param transaction transaction
     */
    public boolean hasTransaction(Transaction transaction) {
        String isExist = "select exists(select 1 from wallet_service.transactions where id = ?);";
        try {
            PreparedStatement isExistUser = connection.prepareStatement(isExist);
            isExistUser.setString(1, transaction.getTransactionID());
            ResultSet isExistsResult = isExistUser.executeQuery();
            while (isExistsResult.next()) {
                return isExistsResult.getBoolean(1);
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
        return false;
    }
}
