package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.User;
import com.github.partezan7.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = repository.findByUsername(username);
        return byUsername.orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    public int countOfUsers() {
        return (int) repository.count();
    }
}
