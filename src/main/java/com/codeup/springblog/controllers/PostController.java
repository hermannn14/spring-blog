package com.codeup.springblog.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String post() {
        return "posts index page";
    }

    @GetMapping("/posts{id}")
    @ResponseBody
    public String postId(@PathVariable int id) {
        return "view an individual post" + id;
    }

    @RequestMapping("/posts/create")
    @ResponseBody
    public String showCreatePostForm() {
        return "view form for creating a new post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String postCreate() {
        return "creating a new post";
    }

}
