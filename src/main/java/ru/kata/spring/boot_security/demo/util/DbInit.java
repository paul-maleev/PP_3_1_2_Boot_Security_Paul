package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class DbInit {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public DbInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        if (roleService.findByRole(admin.getRole()) == null) {
            roleService.save(admin);
        }
        if (roleService.findByRole(user.getRole()) == null) {
            roleService.save(user);
        }
        User userAdmin = new User("admin", "admin", 42, "admin@mail.com");
        User userUser = new User("user", "user", 18, "user@mail.com");
        String[] rolesAdmin = {"ROLE_ADMIN", "ROLE_USER"};
        String[] rolesUser = {"ROLE_USER"};
        if (userService.findByEmail(userAdmin.getEmail()) == null) {
            userService.addUser(userAdmin, rolesAdmin, "admin");
        }
        if (userService.findByEmail(userUser.getEmail()) == null) {
            userService.addUser(userUser, rolesUser, "user");
        }


    }
}