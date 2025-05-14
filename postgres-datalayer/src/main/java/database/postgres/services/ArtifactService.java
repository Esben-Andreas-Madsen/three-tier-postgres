package database.postgres.services;

import database.postgres.DAOs.ArtifactDAO;
import grpc.ArtifactProto;
import shared.DTOs.Artifact;

import java.sql.SQLException;

public class ArtifactService {

    private final ArtifactDAO artifactDAO;

    public ArtifactService(ArtifactDAO artifactDAO) {
        this.artifactDAO = artifactDAO;
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

    /**
     * Creates a new artifact in the database.
     *
     * @param artifact The Artifact object to be created.
     */
    public void createArtifact(ArtifactProto artifact) {
        if (artifact.getPowerLevel() < 0 || artifact.getPowerLevel() > 100) {
            throw new IllegalArgumentException("Power level must be between 0 and 100.");
        }

        try {
            artifactDAO.createArtifact(artifact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the rarity of an artifact.
     *
     * @param id The artifact ID.
     * @param rarity The new rarity.
     */
//    public void updateArtifactRarity(int id, String rarity) {
//        if (!isValidRarity(rarity)) {
//            throw new IllegalArgumentException("Invalid rarity.");
//        }
//
//        artifactDAO.updateArtifactRarity(id, rarity);
//    }

    /**
     * Deletes an artifact by its ID.
     *
     * @param id The artifact ID.
     */
    public void deleteArtifact(int id) {
        artifactDAO.deleteArtifact(id);
    }

    /**
     * Validates the rarity value.
     *
     * @param rarity The rarity to validate.
     * @return true if valid, false otherwise.
     */
    private boolean isValidRarity(String rarity) {
        return rarity.equalsIgnoreCase("Common") ||
                rarity.equalsIgnoreCase("Uncommon") ||
                rarity.equalsIgnoreCase("Rare") ||
                rarity.equalsIgnoreCase("Legendary");
    }
}
