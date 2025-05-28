package database.postgres.DAOs;

import DTOs.Rarity;
import com.zaxxer.hikari.HikariDataSource;
import grpc.ArtifactProto;
import grpc.RarityProto;
import DTOs.Artifact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArtifactDAOImpl implements ArtifactDAO {
    private final HikariDataSource dataSource;
    private static final Logger logger = Logger.getLogger(ArtifactDAOImpl.class.getName());

    public ArtifactDAOImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Artifact createArtifact(Artifact artifact) {
        String insertQuery = "INSERT INTO artifacts (name, origin_story, power_level, rarity, last_known_location, estimated_value) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(insertQuery)) {
            stmt.setString(1, artifact.getName());
            stmt.setString(2, artifact.getOriginStory());
            stmt.setInt(3, artifact.getPowerLevel());
            stmt.setObject(4, artifact.getRarity().name(), java.sql.Types.OTHER);
            stmt.setString(5, artifact.getLastKnownLocation());
            stmt.setInt(6, artifact.getEstimatedValue());

            ResultSet resultSet = stmt.executeQuery();
            logger.info("INSERT: " + artifact);

            if (resultSet.next()) {
                int generatedId = resultSet.getInt("id");  // The generated ID from the database
                artifact.setId(generatedId);  // or create new Artifact with ID set
                return artifact;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert artifact", e);
        }
        return null;
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


        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact", e);
        }

        return null;
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


        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact", e);
        }
        return null;
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

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
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
