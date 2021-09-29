package com.codeup.springblog.controllers;


import com.codeup.springblog.repos.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.springblog.models.Post;


import java.util.ArrayList;
import java.util.List;


@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }


    @GetMapping("/posts")
    public String showPosts(Model model) {
//        List<Post> allPosts = new ArrayList<>();
        List<Post> allPosts = postDao.findAll();


//        allPosts.add(new Post("post!", "post1 body"));
//        allPosts.add(new Post("post@", "post2 body"));

        model.addAttribute("posts", allPosts);
        return "post/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {

//        Post post = new Post("Fun title", "Fun body");
        Post post = postDao.getById(id);

        model.addAttribute("postId", id);
        model.addAttribute("post", post);

        return "post/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String showCreatePostForm() {
        return "post/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name = "title") String title,
                             @RequestParam(name = "body") String body) {

        Post postToAdd = new Post(title, body);
        postDao.save(postToAdd);
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String showEditPostForm(@PathVariable long id, Model model) {
        Post postToEdit = postDao.getById(id);
        model.addAttribute("id", postToEdit.getId());
        return "post/edit";
    }

    @PostMapping("/posts/edit/{id}")
    public String editPost(
            @PathVariable long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body
    ) {
        Post editedPost = new Post(id, title, body);
        postDao.save(editedPost);

        return "redirect:/posts";

    }

    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id) {
        Post postToDelete = postDao.getById(id);
        postDao.delete(postToDelete);

        return "redirect:/posts";

    }
}

