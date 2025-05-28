package database.postgres.services;

import database.postgres.DAOs.ArtifactDAO;
import DTOs.Artifact;

import java.util.List;

public class ArtifactServiceImpl implements ArtifactService {

    private final ArtifactDAO artifactDAO;

    public ArtifactServiceImpl(ArtifactDAO artifactDAO) {
        this.artifactDAO = artifactDAO;
    }

    //TODO implement logic checks

    /**
     * Creates a new artifact in the database.
     *
     * @param artifact The Artifact object to be created.
     * @return
     */
    public Artifact createArtifact(Artifact artifact) {
        if (artifact.getPowerLevel() < 0 || artifact.getPowerLevel() > 100) {
            throw new IllegalArgumentException("Power level must be between 0 and 100.");
        }

        artifactDAO.createArtifact(artifact);
        return artifact;
    }

    /**
     * Fetches an artifact by its ID.
     *
     * @param id The artifact ID.
     * @return The Artifact object or null if not found.
     */
    public Artifact getArtifactById(int id) {
        return artifactDAO.getArtifactById(id);
    }

    @Override
    public Artifact getArtifactByName(String name) {
        return artifactDAO.getArtifactByName(name);
    }

    @Override
    public void updateArtifact(Artifact artifact) {
        artifactDAO.updateArtifact(artifact);
    }

    /**
     * Deletes an artifact by its ID.
     *
     * @param id The artifact ID.
     */
    public void deleteArtifact(int id) {
        artifactDAO.deleteArtifact(id);
    }

    @Override
    public List<Artifact> getAllArtifacts() {
        return artifactDAO.getAllArtifacts();
    }


}
