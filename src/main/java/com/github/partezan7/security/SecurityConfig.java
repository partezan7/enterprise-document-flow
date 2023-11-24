package com.github.partezan7.security;

import com.github.partezan7.data.entity.User;
import com.github.partezan7.data.entity.user.Role;
import com.github.partezan7.data.repository.UserRepository;
import com.github.partezan7.data.service.UserService;
import com.github.partezan7.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;
import java.util.Set;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final UserRepository userRepository;
    private final UserService userService;

    public SecurityConfig(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService users() {
        int count = userService.countOfUsers();

        // if DB is empty then save default users to DB
        if (count == 0) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(getPasswordEncoder().encode("password"));
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userRepository.save(user);

            User userAdmin123 = new User();
            userAdmin123.setUsername("123");
            userAdmin123.setPassword(getPasswordEncoder().encode("password"));
            userAdmin123.setRoles(Set.of(Role.USER, Role.ADMIN));
            userAdmin123.setActive(true);
            userRepository.save(userAdmin123);

            User admin = new User();
            admin.setUsername("admin");
            // password == "password"
            admin.setPassword("$2a$12$SqtwdNgRN2kE7L0ec4.8ze16Sd7mN.hMQ77fq0U461t8Ikkr2LhFa");
            admin.setRoles(Collections.singleton(Role.ADMIN));
            admin.setActive(true);
            userRepository.save(admin);
        }
        return userService;
    }

    @Bean(name = "myPasswordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Autowired
    public DaoAuthenticationProvider getDaoAuthenticationProvider(@Qualifier("myPasswordEncoder") PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}