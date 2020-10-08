package com.codeup.blog.controllers;

import com.codeup.blog.models.User;
import com.codeup.blog.models.Ad;
import com.codeup.blog.repositories.AdRepository;
import com.codeup.blog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("user/{id}")
    public String profilePage(@PathVariable long id,Model model) {
        model.addAttribute("user", userDao.getOne(id));
        return "/users/profile";
    }
}
