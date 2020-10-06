package com.codeup.blog.controllers;

import com.codeup.blog.models.Post;
import com.codeup.blog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepo;

    public PostController(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public String showAllPosts(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {
        Post post = postRepo.getAdById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    @ResponseBody
    public String createPostForm() {
        return "Tell me about your day!";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
    @ResponseBody
    public String sendingCreatedPostToDB() {
        return "Sending your diary page to be seen by everyone. :)";
    }
}
