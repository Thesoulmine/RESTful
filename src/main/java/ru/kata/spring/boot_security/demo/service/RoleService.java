package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface RoleService {

    List<Role> getRolesList();

    @Transactional
    Role findRoleByName(String role);

    @Transactional
    List<Role> convertRolesToList(User user, String[] roles);
}
