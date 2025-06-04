package database.postgres.Tests;

import DTOs.Artifact;
import DTOs.ArtifactHistory;
import DTOs.Rarity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import database.postgres.DAOs.ArtifactDAO;
import database.postgres.DAOs.ArtifactDAOImpl;
import database.postgres.DAOs.ArtifactHistoryDAO;
import database.postgres.DAOs.ArtifactHistoryDAOImpl;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArtifactHistoryDAOImplTest {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init.sql");

    private HikariDataSource dataSource;
    private ArtifactHistoryDAO artifactHistoryDAO;
    private ArtifactDAO artifactDAO;

    @BeforeAll
    void startContainerAndSetup() {
        postgresContainer.start();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgresContainer.getJdbcUrl());
        config.setUsername(postgresContainer.getUsername());
        config.setPassword(postgresContainer.getPassword());
        config.setMaximumPoolSize(1);

        dataSource = new HikariDataSource(config);
        artifactHistoryDAO = new ArtifactHistoryDAOImpl(dataSource);
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
            stmt.execute("TRUNCATE TABLE artifacthistory RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE artifacts RESTART IDENTITY CASCADE");
        }
    }

    @Test
    void testCreateArtifactHistory() {
        // insert an artifact that the history can attach to
        Artifact artifact = new Artifact("Excalibur", "Forged in Avalon", 100, Rarity.Legendary, "Camelot", 1000000);
        Artifact createdArtifact = artifactDAO.createArtifact(artifact);
        int artifactId = createdArtifact.getId();


        // Arrange
        ArtifactHistory artifactHistory = new ArtifactHistory(
                artifactId,
                new Date(2020, Calendar.MARCH,2),
                "Rare sales market test",
                "Mexican Cartel"
        );

        // Act
        ArtifactHistory createdHistory = artifactHistoryDAO.createArtifactHistory(artifactHistory, artifactId);

        // Attempt finding the created artifact
        int createdHistoryId = createdHistory.getId();
        ArtifactHistory fetched = artifactHistoryDAO.getArtifactHistoryById(createdHistoryId);

        // Assert
        Assertions.assertNotNull(fetched);
        Assertions.assertEquals("Mexican Cartel", fetched.getInvolvedParties());
        Assertions.assertEquals(new Date(2020, Calendar.MARCH,2), fetched.getEventDate());
    }
}