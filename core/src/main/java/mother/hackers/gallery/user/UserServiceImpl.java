package mother.hackers.gallery.user;

import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.user.dto.RegistrationUserDto;
import mother.hackers.gallery.user.dto.UserDto;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {

    private static final UserMapper mapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserById(long userId) {
        return userRepository.findById(userId)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException(format("Can not find user with id %s", userId)));
    }

    @Override
    public UserDto createNewUser(RegistrationUserDto dto) {
        User newUser = mapper.toEntity(dto);
        User savedUser = userRepository.save(newUser);

        return mapper.toDto(savedUser);
    }
}
