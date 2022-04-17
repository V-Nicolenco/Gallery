package mother.hackers.gallery.mvc.login;

import mother.hackers.gallery.user.dto.LoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final Validator loginValidator;

    public LoginController(LoginDataValidator loginValidator) {
        this.loginValidator = loginValidator;
    }

    @GetMapping("/login")
    public String view(Model model) {
        LoginDto form = new LoginDto();

        model.addAttribute("loginForm", form);
        return "login";
    }

    @PostMapping("/web-api/login")
    public String register(@ModelAttribute("loginForm") @Validated LoginDto login,
                           BindingResult bindingResult,
                           HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return "redirect:/";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == LoginDto.class) {
            dataBinder.setValidator(loginValidator);
        }
    }
}
