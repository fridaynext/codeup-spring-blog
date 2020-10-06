package com.codeup.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    @ResponseBody
    public String showAllPosts() {
        return "Here all the posts, my dude!";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String showOnePost(@PathVariable long id) {
        return String.format("Here is post number %d", id);
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
