package database.postgres.DAOs;

import DTOs.Rarity;
import com.zaxxer.hikari.HikariDataSource;
import DTOs.Artifact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArtifactDAOImpl implements ArtifactDAO {
    private final HikariDataSource dataSource;
    private static final Logger logger = Logger.getLogger(ArtifactHistoryDAOImpl.class.getName());

    public ArtifactDAOImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Artifact createArtifact(Artifact artifact) {
        String sql = """
                INSERT INTO artifacts (
                name, 
                origin_story, 
                power_level, 
                rarity, 
                last_known_location, 
                estimated_value)
                VALUES (?, ?, ?, ?, ?, ?) RETURNING id
                """;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, artifact.getName());
            stmt.setString(2, artifact.getOriginStory());
            stmt.setInt(3, artifact.getPowerLevel());
            stmt.setObject(4, artifact.getRarity().name(), java.sql.Types.OTHER);
            stmt.setString(5, artifact.getLastKnownLocation());
            stmt.setInt(6, artifact.getEstimatedValue());

            try (ResultSet resultSet = stmt.executeQuery();) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt("id");
                    artifact.setId(generatedId);

                    logger.info("INSERT: " + artifact);
                    return artifact;
                } else {
                    throw new RuntimeException("Artifact insert did not return an ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert artifact", e);
        }
    }

    @Override
    public Artifact getArtifactById(int id) {
        String sql = """
                    SELECT
                        a.id,
                        a.name,
                        a.origin_story,
                        a.power_level,
                        a.rarity,
                        a.last_known_location,
                        a.estimated_value
                    FROM artifacts a
                    WHERE a.id = ?
                """;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    Artifact found = new Artifact();
                    found.setId(rs.getInt("id"));
                    found.setName(rs.getString("name"));
                    found.setOriginStory(rs.getString("origin_story"));
                    found.setPowerLevel(rs.getInt("power_level"));
                    found.setRarity(Rarity.valueOf(found.getRarity().name()));
                    found.setLastKnownLocation(rs.getString("last_known_location"));
                    found.setEstimatedValue(rs.getInt("estimated_value"));

                    logger.info("SELECT: " + found);
                    return found;
                } else {
                    throw new RuntimeException("Artifact with id not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact", e);
        }
    }

    @Override
    public Artifact getArtifactByName(String name) {
        String sql = """
                    SELECT
                        a.id,
                        a.name,
                        a.origin_story,
                        a.power_level,
                        a.rarity,
                        a.last_known_location,
                        a.estimated_value
                    FROM artifacts a
                    WHERE a.name = ?
                """;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Artifact found = new Artifact();
                    found.setId(rs.getInt("id"));
                    found.setName(rs.getString("name"));
                    found.setOriginStory(rs.getString("origin_story"));
                    found.setPowerLevel(rs.getInt("power_level"));
                    found.setRarity(Rarity.valueOf(found.getRarity().name()));
                    found.setLastKnownLocation(rs.getString("last_known_location"));
                    found.setEstimatedValue(rs.getInt("estimated_value"));

                    logger.info("SELECT: " + found);

                    return found;
                } else {
                    throw new RuntimeException("Artifact by name failed");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact", e);
        }
    }

    @Override
    public void updateArtifact(Artifact artifact) {
        String sql = """
                UPDATE artifact
                SET name = ?,
                    origin_story = ?,
                    power_level = ?,
                    rarity = ?,
                    last_known_location = ?,
                    estimated_value = ?
                WHERE id = ?;
                """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, artifact.getName());
            stmt.setString(2, artifact.getOriginStory());
            stmt.setInt(3, artifact.getPowerLevel());
            stmt.setObject(4, artifact.getRarity());
            stmt.setString(5, artifact.getLastKnownLocation());
            stmt.setInt(6, artifact.getEstimatedValue());
            stmt.setInt(7, artifact.getId());
            stmt.executeUpdate();

            logger.info("UPDATED: " + artifact);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update artifact", e);
        }
    }

    @Override
    public void deleteArtifact(int id) {
        String sql = "DELETE FROM artifacts WHERE id = ?";

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artifact", e);
        }
    }

    @Override
    public List<Artifact> getAllArtifacts() {
        String sql = "SELECT * FROM artifacts";
        List<Artifact> artifacts = new ArrayList<>();

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Artifact found = new Artifact();
                found.setId(rs.getInt("id"));
                found.setName(rs.getString("name"));
                found.setOriginStory(rs.getString("origin_story"));
                found.setPowerLevel(rs.getInt("power_level"));
                found.setRarity(Rarity.valueOf(found.getRarity().name()));
                found.setLastKnownLocation(rs.getString("last_known_location"));
                found.setEstimatedValue(rs.getInt("estimated_value"));
                artifacts.add(found);
            }
            return artifacts;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all artifacts", e);
        }
    }
}
