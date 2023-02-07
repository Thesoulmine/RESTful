package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public List<Role> getRolesList() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public Role findRoleByName(String role) {
        return roleRepository.findRoleByName(role);
    }

    @Transactional
    @Override
    public List<Role> convertRolesToList(User user, String[] roles) {
        List<Role> rolesList = new ArrayList<>();
        if (roles != null) {
            for (String role : roles) {
                rolesList.add(findRoleByName(role));
            }
        }
        return rolesList;
    }
}
