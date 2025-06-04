package database.postgres.DAOs;

import DTOs.ArtifactHistory;

public interface ArtifactHistoryDAO {
    ArtifactHistory createArtifactHistory(ArtifactHistory artifactHistory, int artifactId);
    ArtifactHistory getArtifactHistoryById(int id);
    void updateArtifactHistory(ArtifactHistory artifactHistory);
    void deleteArtifactHistory(int id);
}
