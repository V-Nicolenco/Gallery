package mother.hackers.gallery.mvc.registration;


import mother.hackers.gallery.user.UserService;
import mother.hackers.gallery.user.dto.RegistrationUserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final Validator validator;
    private final PasswordEncoder encoder;

    public RegistrationController(UserService userService,
                                  RegistrationDataValidator validator,
                                  PasswordEncoder encoder) {
        this.userService = userService;
        this.validator = validator;
        this.encoder = encoder;
    }

    @GetMapping("/registration")
    public String view(Model model) {
        RegistrationUserDto form = new RegistrationUserDto();

        model.addAttribute("registrationForm", form);
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("registrationForm") @Validated RegistrationUserDto registrationDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        encodePassword(registrationDto);
        userService.createNewUser(registrationDto);
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(registrationDto.getEmail(), registrationDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return "redirect:/index";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == RegistrationUserDto.class) {
            dataBinder.setValidator(validator);
        }
    }

    private void encodePassword(RegistrationUserDto registrationDto) {
        String encodedPassword = encoder.encode(registrationDto.getPassword());
        registrationDto.setPassword(encodedPassword);
    }
}
