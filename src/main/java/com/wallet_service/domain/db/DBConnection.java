package com.wallet_service.domain.db;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Establishes a connection to the database.
 */
public class DBConnection {

    public static Connection connection;

    /**
     * URL for connecting to the database.
     */
    private static String URL;

    /**
     * Database username.
     */
    private static String USER_NAME;

    /**
     * Database password.
     */
    private static String PASSWORD;

    /**
     * Database custom port.
     */
    private static String customPort;

    /**
     * Flag for use custom port.
     */
    private static boolean flag = false;


    /**
     * Parsing connection parameters and establishing a connection.
     */
    public static void startConnection() {
        File file = new File(".env");

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        URL = String.format("jdbc:postgresql://localhost:%s/%s", flag ? customPort : properties.getProperty("PORT"), properties.getProperty("POSTGRES_DB"));
        USER_NAME = properties.getProperty("POSTGRES_USER");
        PASSWORD = properties.getProperty("POSTGRES_PASSWORD");

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Statement createSchema = connection.createStatement();
            createSchema.execute("create schema if not exists migration;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        migration();
    }

    public static void setCustomPort(int port) {
        flag = true;
        customPort = String.valueOf(port);
    }

    /**
     * Closing the connection to the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Database migration.
     */
    public static void migration() {
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setLiquibaseSchemaName("migration");
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}
