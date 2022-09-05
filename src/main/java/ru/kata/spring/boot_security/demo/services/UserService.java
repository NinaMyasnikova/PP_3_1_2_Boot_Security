package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void addNewUser(User user);
    User getUser(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
    void saveUser(User user);
    User findByUsername(String username);
    Long returnId(String name);
}
