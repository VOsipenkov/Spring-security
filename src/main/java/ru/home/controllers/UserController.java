package ru.home.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping
    public String getHome() {
        return "Home";
    }

    @GetMapping(value = "/authenticated")
    public String getAuth() {
        return "Authenticated";
    }
}
