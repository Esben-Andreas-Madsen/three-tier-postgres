package database.postgres.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import grpc.ArtifactProto;
import grpc.RarityProto;
import DTOs.Artifact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public ArtifactProto getArtifactById(int id) {
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
                ArtifactProto found = ArtifactProto.newBuilder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setOriginStory(rs.getString("origin_story"))
                        .setPowerLevel(rs.getInt("power_level"))
                        .setRarity((RarityProto) rs.getObject("rarity"))
                        .setLastKnownLocation(rs.getString("last_known_location"))
                        .setEstimatedValue(rs.getInt("estimated_value"))
                        .build();

                logger.info("SELECT: " + found);

                return found;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact", e);
        }

        return null;
    }

    @Override
    public ArtifactProto getArtifactByName(String name) {
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
                ArtifactProto found = ArtifactProto.newBuilder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setOriginStory(rs.getString("origin_story"))
                        .setPowerLevel(rs.getInt("power_level"))
                        .setRarity(RarityProto.valueOf(rs.getString("rarity")))
                        //stmt.setObject(4, artifact.getRarity().name(), java.sql.Types.OTHER)
                        .setLastKnownLocation(rs.getString("last_known_location"))
                        .setEstimatedValue(rs.getInt("estimated_value"))
                        .build();

                logger.info("SELECT: " + found);

                return found;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact", e);
        }
        return null;
    }

    @Override
    public void updateArtifact(ArtifactProto artifact) {
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
}
