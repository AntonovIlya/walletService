import com.wallet_service.domain.db.DBConnection;
import com.wallet_service.domain.model.BankAccount;
import com.wallet_service.domain.model.Transaction;
import com.wallet_service.domain.model.User;
import com.wallet_service.domain.repository.TransactionRepository;
import com.wallet_service.domain.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DBContainerTest {

    private static final String USER;
    private static final String PASSWORD;
    private static final String DB;
    private static final int PORT;

    private static TransactionRepository transactionRepository;
    private static UserRepository userRepository;
    private static BankAccount userBankAccount;
    private static User user;
    private static Transaction transaction;

    static {
        File file = new File(".env");

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        USER = properties.getProperty("POSTGRES_USER");
        PASSWORD = properties.getProperty("POSTGRES_PASSWORD");
        DB = properties.getProperty("POSTGRES_DB");
        PORT = Integer.parseInt(properties.getProperty("PORT"));
    }

    @Container
    private static final GenericContainer<?> db = new GenericContainer<>("postgres:latest")
            .withEnv("POSTGRES_USER", USER)
            .withEnv("POSTGRES_PASSWORD", PASSWORD)
            .withEnv("POSTGRES_DB", DB)
            .withExposedPorts(PORT);


    @BeforeAll
    static void beforeAll() {
        DBConnection.setCustomPort(db.getMappedPort(PORT));
        DBConnection.startConnection();

        transactionRepository = new TransactionRepository();
        userRepository = new UserRepository();
        userBankAccount = new BankAccount();
        user = new User("login", "password");
        user.setAccount(userBankAccount);
    }

    @AfterAll
    static void closeConnection() {
        DBConnection.closeConnection();
        transactionRepository = null;
        userRepository = null;
        userBankAccount = null;
        user = null;
    }


    @Test
    @Order(1)
    public void addUser() {
        //Arrange
        boolean actual;

        //Act
        userRepository.userRegistration(user);
        actual = userRepository.isExists(user.getLogin(), user.getPassword());

        //Assert
        Assertions.assertTrue(actual);
    }

    @Test
    @Order(2)
    void getUser() {
        //Arrange
        User currentUser;

        //Act
        currentUser = userRepository.getUser(user.getLogin(), user.getPassword());

        //Assert
        Assertions.assertNotNull(currentUser);
        user = currentUser;
        transaction = new Transaction("1", user);

    }

    @Test
    @Order(3)
    void transactionTest() {
        //Arrange
        String typeOperation = "payment";
        boolean actual;

        //Act
        transactionRepository.addTransaction(transaction, typeOperation);
        actual = transactionRepository.hasTransaction(transaction);

        //Assert
        Assertions.assertTrue(actual);
    }
}
