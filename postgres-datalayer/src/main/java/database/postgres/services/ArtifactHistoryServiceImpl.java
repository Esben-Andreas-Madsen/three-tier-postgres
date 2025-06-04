package database.postgres.services;

import DTOs.ArtifactHistory;
import database.postgres.DAOs.ArtifactDAO;
import database.postgres.DAOs.ArtifactHistoryDAO;

public class ArtifactHistoryServiceImpl implements ArtifactHistoryService {
    private final ArtifactHistoryDAO artifactHistoryDAO;

    public ArtifactHistoryServiceImpl(ArtifactHistoryDAO artifactHistoryDAO) {
        this.artifactHistoryDAO = artifactHistoryDAO;
    }

    //TODO implement logic checks

    @Override
    public ArtifactHistory createArtifactHistory(ArtifactHistory artifactHistory, int artifactId) {
        return artifactHistoryDAO.createArtifactHistory(artifactHistory, artifactId);
    }

    @Override
    public ArtifactHistory getArtifactHistoryById(int id) {
        return artifactHistoryDAO.getArtifactHistoryById(id);
    }

    @Override
    public void updateArtifactHistory(ArtifactHistory artifactHistory) {
        artifactHistoryDAO.updateArtifactHistory(artifactHistory);
    }

    @Override
    public void deleteArtifactHistory(int id) {
        artifactHistoryDAO.deleteArtifactHistory(id);
    }
}
