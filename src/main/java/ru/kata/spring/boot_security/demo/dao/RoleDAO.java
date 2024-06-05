package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleDAO {
    Role findByRole (String role);
    void save (Role role);
}
