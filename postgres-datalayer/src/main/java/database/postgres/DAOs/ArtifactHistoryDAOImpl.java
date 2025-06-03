package database.postgres.DAOs;

import DTOs.ArtifactHistory;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.logging.Logger;

public class ArtifactHistoryDAOImpl implements ArtifactHistoryDAO {
    private final HikariDataSource dataSource;
    private static final Logger logger = Logger.getLogger(ArtifactHistoryDAOImpl.class.getName());

    public ArtifactHistoryDAOImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArtifactHistory createArtifactHistory(ArtifactHistory artifactHistory, int artifactId) {
        String sql = "INSERT INTO artifacthistory (artifact_id, event_date, event_description, involved_parties) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, artifactId);

            // Use Timestamp to preserve time component
            stmt.setTimestamp(2, new java.sql.Timestamp(artifactHistory.getEventDate().getTime()));

            stmt.setString(3, artifactHistory.getEventDescriptions());
            stmt.setString(4, artifactHistory.getInvolvedParties());

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt("id");
                    artifactHistory.setId(generatedId);

                    logger.info("INSERT: " + artifactHistory);
                    return artifactHistory;
                } else {
                    throw new RuntimeException("Artifact history insert did not return an ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert artifact", e);
        }
    }


    @Override
    public ArtifactHistory getArtifactHistoryById(int id) {
        String sql = """
                    SELECT
                        ah.id,
                        ah.artifact_id,
                        ah.event_date,
                        ah.event_description,
                        ah.involved_parties
                    FROM artifactHistory ah
                    WHERE ah.id = ?
                """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)

        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ArtifactHistory found = new ArtifactHistory();
                    found.setId(rs.getInt("id"));
                    found.setArtifactId(rs.getInt("artifact_id"));
                    found.setEventDate(rs.getDate("event_date"));
                    found.setEventDescriptions(rs.getString("event_description"));
                    found.setInvolvedParties(rs.getString("involved_parties"));

                    logger.info("SELECT: " + found);

                    return found;
                } else {
                    throw new RuntimeException("Select did not find artifact history.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artifact history", e);
        }
    }

    @Override
    public void updateArtifactHistory(ArtifactHistory artifactHistory) {

    }

    @Override
    public void deleteArtifactHistory(int id) {
        String sql = "DELETE FROM artifacthistory WHERE id = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Deleted artifact history with id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artifact history", e);
        }
    }
}
