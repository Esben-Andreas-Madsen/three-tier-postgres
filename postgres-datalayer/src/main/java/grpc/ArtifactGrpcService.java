package grpc;

import database.postgres.services.ArtifactService;
import database.postgres.services.ArtifactServiceImpl;
import io.grpc.stub.StreamObserver;
import mappers.ArtifactMapper;
import DTOs.Artifact;

import java.util.ArrayList;
import java.util.List;

public class ArtifactGrpcService extends GatewayServiceGrpc.GatewayServiceImplBase {
    private final ArtifactService artifactService;

    public ArtifactGrpcService(ArtifactServiceImpl artifactService) {
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

    @Override
    public void getArtifactById(GetArtifactByIdRequest request, StreamObserver<ArtifactResponse> responseObserver) {
        try {
            int id = request.getId();

            Artifact foundArtifact = artifactService.getArtifactById(id);

            // Map domain model back to proto (with generated ID)
            ArtifactProto artifactProto = ArtifactMapper.INSTANCE.toProto(foundArtifact);

            ArtifactResponse response = ArtifactResponse.newBuilder()
                    .setArtifactproto(artifactProto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getArtifactByName(GetArtifactByNameRequest request, StreamObserver<ArtifactResponse> responseObserver) {
        try {
            Artifact foundArtifact = artifactService.getArtifactByName(request.getName());

            // Map domain model back to proto (with generated ID)
            ArtifactProto artifactProto = ArtifactMapper.INSTANCE.toProto(foundArtifact);

            ArtifactResponse response = ArtifactResponse.newBuilder()
                    .setArtifactproto(artifactProto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateArtifact(UpdateArtifactRequest request, StreamObserver<UpdateArtifactResponse> responseObserver) {
        try {
            // TODO update to return bool through DAO
            Artifact artifact = ArtifactMapper.INSTANCE.toArtifact(request.getArtifactproto());

            artifactService.updateArtifact(artifact);


            responseObserver.onNext(UpdateArtifactResponse.newBuilder().build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteArtifact(DeleteArtifactRequest request, StreamObserver<DeleteArtifactResponse> responseObserver) {
        try {
            // TODO update to return bool through DAO
            artifactService.deleteArtifact(request.getId());

            responseObserver.onNext(DeleteArtifactResponse.newBuilder().build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllArtifacts(GetAllArtifactRequests requests, StreamObserver<ArtifactResponse> responseObserver) {
        try {
            List<Artifact> artifacts = artifactService.getAllArtifacts();
            List<ArtifactResponse> responses = new ArrayList<>();

            for (Artifact a : artifacts) {
                ArtifactProto artifactProto = ArtifactMapper.INSTANCE.toProto(a);
                ArtifactResponse response = ArtifactResponse.newBuilder()
                        .setArtifactproto(artifactProto)
                        .build();
                responses.add(response);
            }

            for (ArtifactResponse response : responses) {
                responseObserver.onNext(response);
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
