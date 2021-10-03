package com.codeup.springblog.controllers;


import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.PostRepository;
import com.codeup.springblog.repos.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.springblog.models.Post;
import com.codeup.springblog.services.EmailService;


import java.util.List;


@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;



    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }


    @GetMapping("/posts")
    public String showPosts(Model model) {
        List<Post> allPosts = postDao.findAll();

        model.addAttribute("posts", allPosts);
        System.out.println("hello");
        return "post/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {

        Post post = postDao.getById(id);

        model.addAttribute("postId", id);
        model.addAttribute("post", post);

        return "post/show";
    }

    @GetMapping("/posts/create")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post postToAdd) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //(@RequestParam(name = "title") String title,
                             //@RequestParam(name = "body") String body) {

//        Post postToAdd = new Post(title, body);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        postToAdd.setOwner(loggedInUser);

        emailService.prepareAndSend(
                postToAdd,
                "new post",
                "You created a new Post"
        );
//        User owner = userDao.getById(1L);
//        Post postToAdd = new Post(title, body, owner);
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
//        Post editedPost = new Post(id, title, body);
//        postDao.save(editedPost);

// get existing info from post
        Post postToUpdate = postDao.getById(id);

        // update it contents
        postToUpdate.setTitle(title);
        postToUpdate.setBody(body);

        // save updated post
        postDao.save(postToUpdate);
        return "redirect:/posts";

    }

    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id) {
        Post postToDelete = postDao.getById(id);
        postDao.delete(postToDelete);

        return "redirect:/posts";

    }
}

