package mappers;

import DTOs.Rarity;
import grpc.ArtifactProto;
import grpc.RarityProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import DTOs.Artifact;

@Mapper
public interface ArtifactMapper {

    ArtifactMapper INSTANCE = Mappers.getMapper(ArtifactMapper.class);

    Artifact toArtifact(ArtifactProto proto);

    ArtifactProto toProto(Artifact artifact);

    // handle the enum conversion
    @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.NULL)
    Rarity map(RarityProto rarity);

    RarityProto map(Rarity rarity);
}
