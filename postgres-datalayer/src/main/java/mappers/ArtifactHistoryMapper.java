package mappers;

import DTOs.ArtifactHistory;
import grpc.ArtifactHistoryProto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TimeStampmapper.class)
public interface ArtifactHistoryMapper {
    ArtifactHistoryMapper INSTANCE = Mappers.getMapper(ArtifactHistoryMapper.class);

    ArtifactHistory toArtifactHistory(ArtifactHistoryProto proto);
    ArtifactHistoryProto toProto(ArtifactHistory artifactHistory);

}
