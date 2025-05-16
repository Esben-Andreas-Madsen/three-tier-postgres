package database.postgres.DAOs;

import grpc.ArtifactProto;

public interface ArtifactDAO {
    ArtifactProto createArtifact(ArtifactProto artifact);
    ArtifactProto getArtifactById(int id);
    ArtifactProto getArtifactByName(String name);
    void updateArtifact(ArtifactProto artifact);
    void deleteArtifact(int id);
}
