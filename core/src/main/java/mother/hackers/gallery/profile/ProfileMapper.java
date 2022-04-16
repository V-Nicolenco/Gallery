package mother.hackers.gallery.profile;

import mother.hackers.gallery.profile.dto.CreateProfileDto;
import mother.hackers.gallery.profile.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);


    Profile toEntity(CreateProfileDto dto);

    @Mapping(source = "owner.id", target = "ownerId")
    ProfileDto toDto(Profile profile);
}
