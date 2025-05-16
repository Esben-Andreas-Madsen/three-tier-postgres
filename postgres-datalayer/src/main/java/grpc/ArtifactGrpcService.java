package grpc;

import database.postgres.services.ArtifactService;
import io.grpc.stub.StreamObserver;

public class ArtifactGrpcService extends GatewayServiceGrpc.GatewayServiceImplBase {
    private final ArtifactService artifactService;

    public ArtifactGrpcService(ArtifactService artifactService) {
        this.artifactService = artifactService; // Service layer dependency injected
    }

    @Override
    public void createArtifact(CreateArtifactRequest request, StreamObserver<ArtifactResponse> responseObserver) {

        artifactService.createArtifact(request.getArtifactproto());

        ArtifactResponse response = ArtifactResponse.newBuilder()
                .setArtifactproto(request.getArtifactproto())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
