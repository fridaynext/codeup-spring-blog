package com.codeup.blog.controllers;

import com.codeup.blog.models.Post;
import com.codeup.blog.models.User;
import com.codeup.blog.repositories.PostRepository;
import com.codeup.blog.repositories.UserRepository;
import com.codeup.blog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final EmailService emailService;

    public PostController(PostRepository postRepo, UserRepository userRepo, EmailService emailService) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String showPosts(Model model) {
        List<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable Integer id, Model model) {
        Post post = postRepo.getPostById(id);
        if (post.getUser() == null) {
            List <User> users = userRepo.findAll();
            post.setUser(users.get(0));
        }
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showCreatePost (Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {

        // Send the create email
        if (post.getId() == 0) {
            post.setUser(userRepo.findAll().get(0)); // kluge to set a current user
            emailService.prepareAndSend(post.getUser().getEmail(),
                    "Created Post: " + post.getTitle(),
                    post.getTitle() + "\n\n" + post.getBody());
        }

        // Send email for an edit
        else {
            post.setUser(postRepo.getPostById(post.getId()).getUser()); // Get the user from the database
            emailService.prepareAndSend(post.getUser().getEmail(),
                    "Edited Post: " + post.getTitle(),
                    post.getTitle() + "\n\n" + post.getBody());
        }
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deleteAd(@PathVariable long id) {
        Post post = postRepo.getPostById(id);
        post.setUser(postRepo.getPostById(post.getId()).getUser()); // Get the user from the database

        // send email for a post delete
        emailService.prepareAndSend(post.getUser().getEmail(),
                "Created Post: " + post.getTitle(),
                post.getTitle() + "\n\n" + post.getBody());
        postRepo.delete(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String editAd(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        model.addAttribute("post", post);
        return "posts/create";
    }

    @PostMapping("/posts/edit")
    public String updateAd(@ModelAttribute Post post) {
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }
}
