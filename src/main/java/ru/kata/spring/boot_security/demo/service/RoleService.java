package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface RoleService {

    List<Role> getRolesList();

    Role findRoleByName(String role);

    void setRolesToUser(User user, String[] roles);
}
