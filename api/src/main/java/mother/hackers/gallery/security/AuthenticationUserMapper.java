package mother.hackers.gallery.security;

import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.dto.RegistrationUserDto;
import mother.hackers.gallery.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticationUserMapper {

    AuthenticationUserMapper INSTANCE = Mappers.getMapper(AuthenticationUserMapper.class);

    AuthenticationUser toAuthentication(User user);
}
