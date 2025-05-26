package database.postgres.DAOs;

import grpc.ArtifactProto;
import DTOs.Artifact;

public interface ArtifactDAO {
    Artifact createArtifact(Artifact artifact);
    // TODO return artifact instead by utilizing mappers
    ArtifactProto getArtifactById(int id);
    ArtifactProto getArtifactByName(String name);
    void updateArtifact(ArtifactProto artifact);
    void deleteArtifact(int id);
}
