package database.postgres.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import grpc.ArtifactProto;
import shared.DTOs.Artifact;
import shared.DTOs.Rarity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ArtifactDAOImpl implements ArtifactDAO{
    private final HikariDataSource dataSource;
    private static final Logger logger = Logger.getLogger(ArtifactDAOImpl.class.getName());

    public ArtifactDAOImpl(HikariDataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public ArtifactProto createArtifact(ArtifactProto artifact) throws SQLException {
        String insertQuery = "INSERT INTO artifacts (name, origin_story, power_level, rarity, last_known_location, estimated_value) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(insertQuery)) {
            stmt.setString(1, artifact.getName());
            stmt.setString(2, artifact.getOriginStory());
            stmt.setInt(3, artifact.getPowerLevel());
            stmt.setObject(4, Rarity.valueOf(artifact.getRarity()).name(), java.sql.Types.OTHER);
            stmt.setString(5, artifact.getLastKnownLocation());
            stmt.setInt(6, artifact.getEstimatedValue());

            ResultSet resultSet = stmt.executeQuery();
            logger.info("INSERT: " + artifact);

            if (resultSet.next()) {
                int generatedId = resultSet.getInt("id");  // The generated ID from the database
                return artifact.toBuilder()               // Use the builder to create the ArtifactProto with the generated ID
                        .setId(generatedId)
                        .build();
            } else {
                throw new SQLException("Failed to insert artifact, no ID returned.");
            }
        }
    }

    @Override
    public Artifact getArtifactById(int id) {
        return null;
    }

    @Override
    public Artifact getArtifactByName(String name) {
        return null;
    }

    @Override
    public void updateArtifact(Artifact artifact) {

    }

    @Override
    public void deleteArtifact(int id) {

    }
}
