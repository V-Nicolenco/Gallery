package mother.hackers.gallery.configuration;

import mother.hackers.gallery.security.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder encoder;

    public SecurityConfiguration(AuthenticationService authenticationService, PasswordEncoder encoder) {
        this.authenticationService = authenticationService;
        this.encoder = encoder;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/js/**", "/css/**", "/webjars/**", "/v2/api-docs", "/swagger-ui.html#/**", "/swagger-resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());

        http.authorizeRequests()
                .mvcMatchers("/login", "/registration", "/api/login", "/api/registration").permitAll()
                .anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(encoder);
    }

}
