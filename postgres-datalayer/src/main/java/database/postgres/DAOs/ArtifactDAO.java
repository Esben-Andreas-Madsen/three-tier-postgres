package database.postgres.DAOs;

import grpc.ArtifactProto;
import shared.DTOs.Artifact;

import java.sql.SQLException;

public interface ArtifactDAO {
    ArtifactProto createArtifact(ArtifactProto artifact);
    Artifact getArtifactById(int id);
    Artifact getArtifactByName(String name);
    void updateArtifact(ArtifactProto artifact);
    void deleteArtifact(int id);
}
