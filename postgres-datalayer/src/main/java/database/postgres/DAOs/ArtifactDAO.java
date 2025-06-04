package database.postgres.DAOs;

import DTOs.Artifact;

import java.util.List;

public interface ArtifactDAO {
    Artifact createArtifact(Artifact artifact);
    Artifact getArtifactById(int id);
    Artifact getArtifactByName(String name);
    // TODO : return boolean to supply info to upper layers for logic
    void updateArtifact(Artifact artifact);
    void deleteArtifact(int id);
    List<Artifact> getAllArtifacts();
}
