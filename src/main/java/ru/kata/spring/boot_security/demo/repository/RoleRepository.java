package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(@Param("role") String role);
}
