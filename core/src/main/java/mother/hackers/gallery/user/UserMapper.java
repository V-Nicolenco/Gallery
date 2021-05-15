package mother.hackers.gallery.user;

import mother.hackers.gallery.user.dto.RegistrationUserDto;
import mother.hackers.gallery.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto dto);

    User toEntity(RegistrationUserDto dto);

    UserDto toDto(User user);
}
