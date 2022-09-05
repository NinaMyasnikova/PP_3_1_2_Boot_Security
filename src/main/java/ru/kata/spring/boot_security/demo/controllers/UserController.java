package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/{id}")
    public String printUsers(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }

    @GetMapping(value = "/403")
    public String accessDenied() {
        return "403";
    }
}
