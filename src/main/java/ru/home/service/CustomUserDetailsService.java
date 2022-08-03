package ru.home.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.home.persistence.Role;
import ru.home.persistence.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private List<User> users;

    @PostConstruct
    public void init() {
        users = List.of(
            new User("user", "100", new Role("USER")),
            new User("admin", "100", new Role("ADMIN"))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
            .filter(u -> Objects.equals(u.getUsername(), username))
            .map(u -> new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), List.of(new SimpleGrantedAuthority(u.getRole().getName()))
            ))
            .findFirst().orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
