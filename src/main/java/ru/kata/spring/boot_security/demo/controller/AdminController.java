package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserServiceImpl userService;

    private final RoleServiceImpl roleService;

    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String showAdminPage(ModelMap model, Principal principal) {
        model.addAttribute("currentUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("newUser", new User());
        model.addAttribute("newUserRoles", roleService.getRolesList());
        return "admin";
    }

    @PostMapping(value = "/addUser")
    public String saveUser(@ModelAttribute("user") @Validated User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(name = "allRoles", required = false) String[] roles) {
        user.setRoles(roleService.convertRolesToList(user, roles));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
        User user = new User();
        user.setId(id);
        userService.deleteUser(user);
        return "redirect:/admin";
    }
}
