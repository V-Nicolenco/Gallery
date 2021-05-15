package mother.hackers.gallery.security;

import mother.hackers.gallery.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class AuthenticationService implements UserDetailsService {

    private static final AuthenticationUserMapper mapper = AuthenticationUserMapper.INSTANCE;

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findUserByEmail(email)
                .map(mapper::toAuthentication)
                .orElseThrow(() -> new UsernameNotFoundException(format("No users with email %s were found.", email)));
    }

}
