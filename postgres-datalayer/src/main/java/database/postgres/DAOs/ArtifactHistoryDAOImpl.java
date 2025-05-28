package database.postgres.DAOs;

import DTOs.ArtifactHistory;
import DTOs.Rarity;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ArtifactHistoryDAOImpl implements ArtifactHistoryDAO {
    private final HikariDataSource dataSource;
    private static final Logger logger = Logger.getLogger(ArtifactDAOImpl.class.getName());

    public ArtifactHistoryDAOImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArtifactHistory createArtifactHistory(ArtifactHistory artifactHistory, int artifactId) {

        // Insert artifactHistory
        String insertQuery = "INSERT INTO artifacthistory (artifact_id, event_date, event_descriptions, involved_parties) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(insertQuery)) {
            stmt.setInt(1, artifactId);
            stmt.setDate(2, new Date(artifactHistory.getEventDate().getTime()));
            stmt.setString(3, artifactHistory.getEventDescriptions());
            stmt.setString(4, artifactHistory.getInvolvedParties());

            ResultSet resultSet = stmt.executeQuery();
            logger.info("INSERT: " + artifactHistory);

            if (resultSet.next()) {
                int generatedId = resultSet.getInt("id");  // The generated ID from the database
                artifactHistory.setId(generatedId);  // or create new Artifact with ID set
                return artifactHistory;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert artifact", e);
        }
        return null;
    }

    @Override
    public ArtifactHistory getArtifactHistoryById(int id) {
        String sql = """
                    SELECT
                        ah.id,
                        ah.artifact_id,
                        ah.event_date,
                        ah.event_descriptions,
                        ah.involved_parties,
                    FROM artifactHistory ah
                    WHERE ah.id = ?
                """;

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ArtifactHistory found = new ArtifactHistory();
                found.setId(rs.getInt("id"));
                found.setArtifactId(rs.getInt("artifact_id"));
                found.setEventDate(rs.getDate("event_date"));
                found.setEventDescriptions(rs.getString("event_descriptions"));
                found.setInvolvedParties(rs.getString("involved_parties"));

                logger.info("SELECT: " + found);

                return found;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact history", e);
        }

        return null;
    }

    @Override
    public void updateArtifactHistory(int id) {

    }

    @Override
    public void deleteArtifactHistory(int id) {
        String sql = "DELETE FROM artifacthistory WHERE id = ?";

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artifact history", e);
        }
    }
}
