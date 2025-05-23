package grpc;

import database.postgres.services.ArtifactService;
import io.grpc.stub.StreamObserver;
import mappers.ArtifactMapper;
import shared.DTOs.Artifact;

public class ArtifactGrpcService extends GatewayServiceGrpc.GatewayServiceImplBase {
    private final ArtifactService artifactService;

    public ArtifactGrpcService(ArtifactService artifactService) {
        this.artifactService = artifactService; // Service layer dependency injected
    }

    @Override
    public void createArtifact(CreateArtifactRequest request, StreamObserver<ArtifactResponse> responseObserver) {
        try {
            // Map proto to domain model
            Artifact artifact = ArtifactMapper.INSTANCE.toArtifact(request.getArtifactproto());

            // Call domain service which returns saved artifact with generated ID
            Artifact savedArtifact = artifactService.createArtifact(artifact);

            // Map domain model back to proto (with generated ID)
            ArtifactProto artifactProto = ArtifactMapper.INSTANCE.toProto(savedArtifact);

            ArtifactResponse response = ArtifactResponse.newBuilder()
                    .setArtifactproto(artifactProto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
