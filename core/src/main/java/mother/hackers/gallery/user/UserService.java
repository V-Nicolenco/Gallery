package mother.hackers.gallery.user;

import mother.hackers.gallery.user.dto.RegistrationUserDto;
import mother.hackers.gallery.user.dto.UserDto;

public interface UserService {

    UserDto getUserById(long userId);

    UserDto createNewUser(RegistrationUserDto dto);
}
