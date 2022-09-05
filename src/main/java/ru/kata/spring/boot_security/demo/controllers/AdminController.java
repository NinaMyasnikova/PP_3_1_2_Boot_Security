package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/users")
    public String printUsers(ModelMap model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/admin/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.listRoles());
        return "newUser";
    }

    @PostMapping(value = "/admin/save")
    public String saveUser(@ModelAttribute(value = "user") User user) {
        userService.addNewUser(user);
        return "redirect:/admin/users";
    }

    @PutMapping (value = "/admin/edit/{id}")
    public String editUser(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.listRoles());
        return "editUser";
    }

    @PatchMapping(value = "/admin/{id}")
    public String updateUser(@ModelAttribute(value = "user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
