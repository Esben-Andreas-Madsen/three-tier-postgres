package database.postgres.DAOs;

import DTOs.ArtifactHistory;

public interface ArtifactHistoryDAO {
    ArtifactHistory createArtifactHistory(ArtifactHistory artifactHistory, String artifactName);
    ArtifactHistory getArtifactHistoryById(int id);
    void updateArtifactHistory(int id);
    void deleteArtifactHistory(int id);
}
