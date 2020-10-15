package com.codeup.blog.controllers;

import com.codeup.blog.models.User;
import com.codeup.blog.models.Ad;
import com.codeup.blog.repositories.AdRepository;
import com.codeup.blog.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder; // Added so we can hash passwords!

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("user/{id}")
    public String profilePage(@PathVariable long id,Model model) {
        model.addAttribute("user", userDao.getOne(id));
        return "/users/profile";
    }

    // Add a GET and POST mapping for site registration
    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "/users/signup";
    }
    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        return "redirect:/login"; // this takes them back to the login page after registering
            // but we can also redirect them to the profile page immediately, if we want
    }
}
