package database.postgres.services;

import DTOs.ArtifactHistory;

public interface ArtifactHistoryService {
    ArtifactHistory createArtifactHistory(ArtifactHistory artifactHistory, int artifactId);
    ArtifactHistory getArtifactHistoryById(int id);
    void updateArtifactHistory(int id);
    void deleteArtifactHistory(int id);
}
