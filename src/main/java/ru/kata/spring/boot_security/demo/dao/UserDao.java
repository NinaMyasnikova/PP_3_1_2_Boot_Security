package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao{

    void addNewUser(User user);
    User getUser(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
    void editUser(User user);
    User returnUserByName(String username);
}
