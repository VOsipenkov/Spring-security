package ru.home.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    @GetMapping
    public String getHome() {
        return "Home";
    }

    @GetMapping(value = "/authenticated")
    public String getAuth(Principal principal) {
        return "Authenticated by " + principal.getName();
    }
}
