package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService, PasswordEncoder passwordEncoder) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin").hasAuthority("ADMIN")
                    .antMatchers("/user").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers(HttpMethod.GET,"/api/users/current").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers(HttpMethod.GET,"/api/users/roles").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/api/users/{id}").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.PUT,"/api/users").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.POST,"/api/users").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET,"/api/users/{id}").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET,"/api/users").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/login")
                    .successHandler(successUserHandler)
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
        return authenticationProvider;
    }
}