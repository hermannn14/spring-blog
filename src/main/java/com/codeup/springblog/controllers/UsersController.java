package com.codeup.springblog.controllers;

import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    private UserRepository users;
    private PasswordEncoder passwordEncoder;

    public UsersController(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);
        return "redirect:/login";
    }


}




//@Controller
//public class UsersController {
//
//    @GetMapping ("user/create")
//    public String createUserForm() {
//        return "user/create";
//    }
//
//    @PostMapping("/user/create")
//    public String createUser(
//            @RequestParam(name ="uname") String username,
//            @RequestParam(name="psw") String password
//    ){
//        System.out.println("Username" + username);
//        System.out.println("Password" + password);
//        return "user/create";
//
//    }
//}
