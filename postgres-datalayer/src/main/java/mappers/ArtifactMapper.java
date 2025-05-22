package mappers;

import grpc.ArtifactProto;
import grpc.RarityProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import shared.DTOs.Artifact;
import shared.DTOs.Rarity;

@Mapper
public interface ArtifactMapper {

    ArtifactMapper INSTANCE = Mappers.getMapper(ArtifactMapper.class);

    Artifact toArtifact(ArtifactProto proto);

    ArtifactProto toProto(Artifact artifact);

    // handle the enum conversion
    @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.NULL)
    shared.DTOs.Rarity map(RarityProto rarity);

    RarityProto map(shared.DTOs.Rarity rarity);
}
