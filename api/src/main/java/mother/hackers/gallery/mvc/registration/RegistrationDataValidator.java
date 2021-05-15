package mother.hackers.gallery.mvc.registration;

import mother.hackers.gallery.user.UserRepository;
import mother.hackers.gallery.user.dto.RegistrationUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Service
public class RegistrationDataValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationDataValidator.class);

    private final UserRepository userRepository;

    public RegistrationDataValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof RegistrationUserDto) {
            RegistrationUserDto reg = (RegistrationUserDto) target;

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.empty.firstname");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.empty.lastname");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.empty.email");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.empty.password");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.empty.confirmPassword");

            if (!isEmail(reg.getEmail())) {
                errors.rejectValue("email", "error.invalid.email");
            }
            if (userRepository.findUserByEmail(reg.getEmail()).isPresent()) {
                errors.rejectValue("email", "error.duplicate.email");
            }
            if (!reg.getPassword().equals(reg.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "error.match.password");
            }
        } else {
            logger.error("Incorrect type of target in RegValidator");
        }
    }

    private boolean isEmail(String email) {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }
}
