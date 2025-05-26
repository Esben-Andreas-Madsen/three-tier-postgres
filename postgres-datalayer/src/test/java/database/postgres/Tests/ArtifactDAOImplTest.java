package database.postgres.Tests;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import database.postgres.DAOs.ArtifactDAO;
import database.postgres.DAOs.ArtifactDAOImpl;
import mappers.ArtifactMapper;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import DTOs.Artifact;
import DTOs.Rarity;

import java.sql.Connection;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArtifactDAOImplTest {

    // Start PostgreSQL container with init.sql executed
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init.sql"); // runs schema setup automatically

    private HikariDataSource dataSource;
    private ArtifactDAO artifactDAO;

    @BeforeAll
    void startContainerAndSetup() {
        postgresContainer.start();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgresContainer.getJdbcUrl());
        config.setUsername(postgresContainer.getUsername());
        config.setPassword(postgresContainer.getPassword());
        config.setMaximumPoolSize(2);

        dataSource = new HikariDataSource(config);
        artifactDAO = new ArtifactDAOImpl(dataSource);
    }

    @AfterAll
    void stopContainer() {
        if (dataSource != null) {
            dataSource.close();
        }
        postgresContainer.stop();
    }

    @BeforeEach
    void cleanTable() throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE artifacts RESTART IDENTITY CASCADE");
        }
    }

    @Test
    void testCreateArtifact() {
        // Arrange
        Artifact artifact = new Artifact("Excalibur", "Forged in Avalon", 100, Rarity.Legendary, "Camelot", 1000000);

        // Act
        artifactDAO.createArtifact(artifact);

        // Attempt finding the created artifact
        Artifact fetched = artifactDAO.getArtifactByName("Excalibur");

        // Assert
        Assertions.assertNotNull(fetched);
        Assertions.assertEquals("Excalibur", fetched.getName());
        Assertions.assertEquals(Rarity.Legendary, fetched.getRarity());
        Assertions.assertEquals("Camelot", fetched.getLastKnownLocation());
    }
}
