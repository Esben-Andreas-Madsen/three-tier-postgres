package database.postgres.services;

import database.postgres.DAOs.ArtifactDAO;
import grpc.ArtifactProto;
import DTOs.Artifact;

public class ArtifactService {

    private final ArtifactDAO artifactDAO;

    public ArtifactService(ArtifactDAO artifactDAO) {
        this.artifactDAO = artifactDAO;
    }

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
    public ArtifactProto getArtifactById(int id) {
        return artifactDAO.getArtifactById(id);
    }

    /**
     * Deletes an artifact by its ID.
     *
     * @param id The artifact ID.
     */
    public void deleteArtifact(int id) {
        artifactDAO.deleteArtifact(id);
    }

    public void updateArtifact(){

    }


}
