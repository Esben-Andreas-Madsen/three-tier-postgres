package database.postgres.DAOs;

import grpc.ArtifactProto;
import shared.DTOs.Artifact;

import java.sql.SQLException;

public interface ArtifactDAO {
    ArtifactProto createArtifact(ArtifactProto artifact) throws SQLException;
    Artifact getArtifactById(int id);
    Artifact getArtifactByName(String name);
    void updateArtifact(Artifact artifact);
    void deleteArtifact(int id);
}
