package mother.hackers.gallery.user;

import mother.hackers.gallery.user.dto.RegistrationUserDto;
import mother.hackers.gallery.user.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserDto getUserById(long userId);

    UserDto createNewUser(RegistrationUserDto dto);
}
