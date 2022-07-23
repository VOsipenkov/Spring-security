package ru.home.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceDetails implements UserDetailsService {
    private List<User> users;

    @PostConstruct
    public void init() {
        users = List.of(
            new User("vlad", "1234"),
            new User("anna", "hello")
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
            .filter(user -> Objects.equals(user.getUsername(), username))
            .findFirst().get();
    }
}
