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

import java.sql.SQLException;

import static com.wallet_service.domain.db.DBConnection.connection;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DBContainerTest {

    private static TransactionRepository transactionRepository;
    private static UserRepository userRepository;
    private static BankAccount userBankAccount;
    private static User user;
    private static Transaction transaction;

    @Container
    private static final GenericContainer<?> db = new GenericContainer<>("postgres:latest")
            .withEnv("POSTGRES_USER", "pguser")
            .withEnv("POSTGRES_PASSWORD", "pgpassword")
            .withEnv("POSTGRES_DB", "pgdb")
            .withExposedPorts(5432);

    @BeforeAll
    static void beforeAll() {
        int port = db.getMappedPort(5432);
        DBConnection.setCustomPort(port);
        DBConnection.startConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        transactionRepository = new TransactionRepository();
        userRepository = new UserRepository();
        userBankAccount = new BankAccount();
        user = new User("login", "password");
        user.setAccount(userBankAccount);
    }

    @AfterAll
    static void closeConnection() {
        transactionRepository = null;
        userRepository = null;
        userBankAccount = null;
        user = null;
        DBConnection.closeConnection();
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
