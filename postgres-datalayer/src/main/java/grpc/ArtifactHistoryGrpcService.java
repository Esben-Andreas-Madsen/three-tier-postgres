package grpc;

import DTOs.ArtifactHistory;
import database.postgres.services.ArtifactHistoryService;
import io.grpc.stub.StreamObserver;
import mappers.ArtifactHistoryMapper;

public class ArtifactHistoryGrpcService extends GatewayServiceGrpc.GatewayServiceImplBase {
    private ArtifactHistoryService artifactHistoryService;

    public ArtifactHistoryGrpcService(ArtifactHistoryService artifactHistoryService) {
        this.artifactHistoryService = artifactHistoryService;
    }

    @Override
    public void createArtifactHistory(CreateArtifactHistoryRequest request, StreamObserver<ArtifactHistoryResponse> responseObserver) {
        try {
            ArtifactHistory artifactHistory = ArtifactHistoryMapper.INSTANCE.toArtifactHistory(request.getArtifactHistoryProto());
            int artifactId = request.getArtifactId();

            ArtifactHistory savedArtifactHistory = artifactHistoryService.createArtifactHistory(artifactHistory, artifactId);

            // Map domain model back to proto (with generated ID)
            ArtifactHistoryProto artifactHistoryProto = ArtifactHistoryMapper.INSTANCE.toProto(savedArtifactHistory);

            ArtifactHistoryResponse response = ArtifactHistoryResponse.newBuilder()
                    .setArtifactHistoryProto(artifactHistoryProto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getArtifactHistoryById(GetArtifactHistoryByIdRequest request, StreamObserver<ArtifactHistoryResponse> responseObserver) {
        try {
            int id = request.getId();

            ArtifactHistory foundArtifact = artifactHistoryService.getArtifactHistoryById(id);

            // Map domain model back to proto (with generated ID)
            ArtifactHistoryProto artifactHistoryProto = ArtifactHistoryMapper.INSTANCE.toProto(foundArtifact);

            ArtifactHistoryResponse response = ArtifactHistoryResponse.newBuilder()
                    .setArtifactHistoryProto(artifactHistoryProto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteArtifactHistory(DeleteArtifactHistoryRequest request, StreamObserver<DeleteArtifactHistoryResponse> responseObserver) {
        try {
            artifactHistoryService.deleteArtifactHistory(request.getId());

            responseObserver.onNext(DeleteArtifactHistoryResponse.newBuilder().build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
