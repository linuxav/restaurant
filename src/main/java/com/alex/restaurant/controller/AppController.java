package com.alex.restaurant.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.alex.restaurant.entity.User;
import com.alex.restaurant.exception.DuplicateRegistrationException;
import com.alex.restaurant.service.UserService;

@Controller
public class AppController {

    private final UserService userService;

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/index", "/home"})
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);
            return "register_success";
        } catch (DuplicateRegistrationException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "signup_form";
        }
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }
}
