//package database.postgres.Tests;
//
//import database.postgres.DAOs.RoleDAO;
//import database.postgres.DAOs.UserDAO;
//import database.postgres.DatabaseConnection;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.*;
//import shared.Role;
//import shared.User;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserDAOTest {
//
//    private static Connection connection;
//    private static UserDAO userDAO;
//    private static User user;
//    private static final Logger logger = LogManager.getLogger(UserDAO.class);
//
//    @BeforeAll
//    static void initDatabase() throws SQLException {
//        connection = new DatabaseConnection().getConnection();
//        connection.setAutoCommit(false); // Roll back after each test
//    }
//
//
//    @BeforeEach
//    void setUp() {
//        // Initialize DAOs for each test
//        RoleDAO roleDAO = new RoleDAO();
//        userDAO = new UserDAO();
//
//        user = new User();
//        user.setUsername("testuser");
//        user.setEmail("test@example.com");
//        user.setPasswordHash("hashed_pw");
//    }
//
//    @AfterEach
//    void rollback() throws SQLException {
//        // Rollback the transaction after each test to ensure no changes persist
//        connection.rollback();
//    }
//
//    @AfterAll
//    static void closeConnection() throws SQLException {
//        connection.close();
//    }
//
//    @Test
//    public void testCreateAndGetUser() {
//        // Test user creation logic
//        User user = new User();
//        user.setUsername("TEST_USER");
//        user.setEmail("TEST@EXAMPLE.COM");
//        user.setPasswordHash("HASHED_PW");
//
//        User shouldNotExist = userDAO.getUserByUsername(connection, "TEST_USER");
//        logger.info("tried to fetch user and got: " + shouldNotExist);
//        assertNull(shouldNotExist);
//
//        userDAO.createUser(connection, user);
//        logger.info("created user: " + user);
//
//        User fromDb = userDAO.getUserByUsername(connection, user.getUsername());
//        logger.info("fetched user: " + fromDb);
//        assertNotNull(fromDb);
//        assertNotNull(fromDb.getCreatedAt());
//        assertTrue(fromDb.getId() >= 0);
//        assertEquals(user.getUsername(), fromDb.getUsername());
//        assertEquals(user.getEmail(), fromDb.getEmail());
//        assertEquals(user.getPasswordHash(), fromDb.getPasswordHash());
//    }
//
//    @Test
//    public void testGetUserById() {
//        // Test getting user by ID
//        User user = new User();
//        user.setUsername("testuser");
//        user.setEmail("test@example.com");
//        user.setPasswordHash("hashed_pw");
//
//        userDAO.createUser(connection, user);
//        logger.info("created user: " + user);
//
//
//        User fromDb = userDAO.getUserById(connection, user.getId());
//        logger.info("fetched user by id: " + user);
//
//        assertNotNull(fromDb, "User should not be null when fetched by ID");
//        assertEquals(user.getUsername(), fromDb.getUsername(), "Usernames should match");
//    }
//
//    @Test
//    public void testUpdateUserEmail() {
//        // Test updating user email
//        User user = new User();
//        user.setUsername("TEST_USER");
//        user.setEmail("TEST@EXAMPLE.COM");
//        user.setPasswordHash("HASHED_PW");
//
//        userDAO.createUser(connection, user);
//        logger.info("created user: " + user);
//
//        String newEmail = "NEW@EXAMPLE.COM";
//        userDAO.updateUserEmail(connection, user.getId(), newEmail);
//
//        User updated = userDAO.getUserById(connection, user.getId());
//        logger.info("updated user is: " + updated);
//
//        assertEquals(newEmail, updated.getEmail(), "User email should be updated");
//    }
//
//    @Test
//    public void testDeleteUser() {
//        // Test deleting user
//        User user = new User();
//        user.setUsername("TEST_USER");
//        user.setEmail("TEST@EXAMPLE.COM");
//        user.setPasswordHash("HASHED_PW");
//
//        userDAO.createUser(connection, user);
//        logger.info("created user: " + user);
//
//        userDAO.deleteUser(connection, user.getId());
//
//        User deletedUser = userDAO.getUserById(connection, user.getId());
//        logger.info("deleted user: " + deletedUser);
//
//
//        assertNull(deletedUser, "User should be null after deletion");
//    }
//}
