package database.postgres.services;

import DTOs.Artifact;

import java.util.List;

public interface ArtifactService {
    Artifact createArtifact(Artifact artifact);
    Artifact getArtifactById(int id);
    Artifact getArtifactByName(String name);
    void updateArtifact(Artifact artifact);
    void deleteArtifact(int id);
    List<Artifact> getAllArtifacts();
}
