package com.codeup.blog.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class AuthenticationController {
    // Add functionality for login page
    @GetMapping("/login")
    public String loginFunc(Model model) {
        // Check if they tried to log in and failed, and sticky that username
        return "/users/login";
    }

}
