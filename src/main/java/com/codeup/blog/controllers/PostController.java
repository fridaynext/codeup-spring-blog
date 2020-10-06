package com.codeup.blog.controllers;

import com.codeup.blog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public String showAllPosts(Model model) {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post(0, "First Post", "This is the first Post"));
        postList.add(new Post(0, "Second Post", "This is the second Post"));
        model.addAttribute("posts", postList);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {
        Post post = new Post(0, "Single Post", "This is the boday for the single post");
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
