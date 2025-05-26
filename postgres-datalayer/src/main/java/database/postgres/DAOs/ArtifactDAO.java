package database.postgres.DAOs;

import grpc.ArtifactProto;
import DTOs.Artifact;

public interface ArtifactDAO {
    Artifact createArtifact(Artifact artifact);
    // TODO return artifact instead by utilizing mappers
    Artifact getArtifactById(int id);
    Artifact getArtifactByName(String name);
    void updateArtifact(Artifact artifact);
    void deleteArtifact(int id);
}
