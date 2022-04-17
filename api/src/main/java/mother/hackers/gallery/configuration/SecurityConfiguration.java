package mother.hackers.gallery.configuration;

import mother.hackers.gallery.security.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

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
                .antMatchers(HttpMethod.OPTIONS,"*").permitAll()//allow CORS option calls
                .mvcMatchers("/login", "/registration", "/web-api/login", "/web-api/registration", "/").permitAll()
                .anyRequest().authenticated();

        http.cors().configurationSource(request -> configureCors());

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profiles/me", true)
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

    private CorsConfiguration configureCors() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        return corsConfiguration;
    }
}