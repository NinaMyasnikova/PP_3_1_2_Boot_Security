package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final BCryptPasswordEncoder bPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Lazy BCryptPasswordEncoder bPasswordEncoder) {
        this.userDao = userDao;
        this.bPasswordEncoder = bPasswordEncoder;
    }

    @Transactional
    @Override
    public void editUser(User user) {
        if (!Objects.equals(getUser(user.getId()).getPassword(), user.getPassword())) {
            user.setPassword(bPasswordEncoder.encode(user.getPassword()));
        }
        userDao.editUser(user);
    }

    @Transactional
    @Override
    public void addNewUser(User user) {
        user.setPassword(bPasswordEncoder.encode(user.getPassword()));
        userDao.addNewUser(user);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User returnUserByName(String username) {
        return userDao.returnUserByName(username);
    }

    @Override
    public Long returnId(String name) {
        return userDao.returnUserByName(name).getId();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = returnUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getAuthorities());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
