package mother.hackers.gallery.configuration;

import mother.hackers.gallery.security.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

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

        http.authorizeRequests()
                .mvcMatchers("/login", "/registration", "/web-api/login", "/web-api/registration").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profiles/me", true)
                .and()
                .rememberMe()
                .tokenValiditySeconds(604800)
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .logoutUrl("/logout");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(encoder);
    }

}
