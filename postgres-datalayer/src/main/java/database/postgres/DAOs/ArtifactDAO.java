package database.postgres.DAOs;

import grpc.ArtifactProto;
import shared.DTOs.Artifact;

public interface ArtifactDAO {
    Artifact createArtifact(Artifact artifact);
    ArtifactProto getArtifactById(int id);
    ArtifactProto getArtifactByName(String name);
    void updateArtifact(ArtifactProto artifact);
    void deleteArtifact(int id);
}
