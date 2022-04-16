package mother.hackers.gallery.user;

import mother.hackers.gallery.user.dto.RegistrationUserDto;
import mother.hackers.gallery.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto dto);

    @Mappings(@Mapping(target = "passwordHash", source = "password"))
    User toEntity(RegistrationUserDto dto);

    UserDto toDto(User user);
}
