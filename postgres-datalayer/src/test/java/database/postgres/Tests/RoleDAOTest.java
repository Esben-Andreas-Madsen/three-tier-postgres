package database.postgres.Tests;

import database.postgres.DAOs.RoleDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import shared.Role;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class RoleDAOTest {
    private static Connection connection;
    private RoleDAO roleDAO;
    private static final Logger logger = LogManager.getLogger(RoleDAO.class);

    @BeforeAll
    static void initDatabase() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/threetier_db",
                "admin",
                "admin"
        );
        connection.setAutoCommit(false); // Roll back after each test
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        connection.close();
    }

    @BeforeEach
    void setUp() {
        roleDAO = new RoleDAO();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
    }

    @Test
    void testCreateAndGetRole() {
        Role role = new Role("TEST_ROLE");

        roleDAO.createRole(connection, role);
        logger.info("created role: " + role);

        assertTrue(role.getId() > 0);

        Role fetched = roleDAO.getRoleById(connection, role.getId());
        assertNotNull(fetched);
        logger.info("fetched role: " + fetched);
        assertEquals("TEST_ROLE", fetched.getName());
    }

    @Test
    void testUpdateRole() {
        Role role = new Role("ORIGINAL");
        roleDAO.createRole(connection, role);
        logger.info("created role: " + role);


        role.setName("UPDATED");
        roleDAO.updateRole(connection, role);
        logger.info("updated role to: " + role);


        Role updated = roleDAO.getRoleById(connection, role.getId());
        logger.info("fetched role by Id: " + updated);
        assertEquals("UPDATED", updated.getName());
    }

    @Test
    void testDeleteRole() {
        Role role = new Role("TO_DELETE");
        roleDAO.createRole(connection, role);

        logger.info("created role: " + role);

        roleDAO.deleteRole(connection, role.getId());

        logger.info("deleted role: " + role);

        Role deleted = roleDAO.getRoleById(connection, role.getId());
        logger.info("role is now: " + deleted);
        assertNull(deleted);
    }
}
