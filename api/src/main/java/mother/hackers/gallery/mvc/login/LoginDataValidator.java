package mother.hackers.gallery.mvc.login;

import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import mother.hackers.gallery.user.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

import static mother.hackers.gallery.utils.ValidationUtils.isEmail;

@Service
public class LoginDataValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(LoginDataValidator.class);

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public LoginDataValidator(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof LoginDto) {
            LoginDto login = (LoginDto) target;

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.empty.email");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.empty.password");

            if (!isEmail(login.getEmail())) {
                errors.rejectValue("email", "error.invalid.email");
            }

            Optional<User> user = userRepository.findUserByEmail(login.getEmail());
            if (user.isEmpty()) {
                errors.rejectValue("email", "error.invalid.credentials");
            } else {
                String dbPassword = user.get().getPasswordHash();

                if (!encoder.matches(login.getPassword(), dbPassword)) {
                    errors.rejectValue("password", "error.invalid.credentials");
                }
            }
        } else {
            logger.error("Incorrect type of target in LoginValidator");
        }
    }
}
