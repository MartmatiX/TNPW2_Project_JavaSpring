package com.tnpw2.project.controllers;

import com.tnpw2.project.database_operations.PostService;
import com.tnpw2.project.database_operations.UserService;
import com.tnpw2.project.post_objects.Post;
import com.tnpw2.project.user_objects.Type;
import com.tnpw2.project.user_objects.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class FormController {

    private final UserService userService;
    private final PostService postService;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public FormController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
        this.encoder = new BCryptPasswordEncoder();
    }

    @PostMapping(path = "/validate_registration")
    public String validate_registration(@ModelAttribute User formUser) {
        User user = new User(formUser.getId(), formUser.getName(), formUser.getSurname(), formUser.getUsername(), formUser.getEmail(), encoder.encode(formUser.getPassword()), Type.User, 0);
        // TODO: 01.03.2023 validate formUser data
        if (userService.registerUser(user)) {
            return "redirect:index?registration=successful";
        }
        return "redirect:index?registration=failure";
    }

    @PostMapping(path = "/validate_login")
    public String validate_login(@ModelAttribute Optional<User> user, HttpSession session) {
        // TODO: 03.03.2023 rewrite to Spring security
        if (user.isPresent()) {
            if (user.get().getUsername().isEmpty()) {
                return "redirect:login_page?username=empty";
            }
            if (user.get().getPassword().isEmpty()) {
                return "redirect:login_page?password=empty";
            }
            Optional<User> byUsernameOptional = userService.findByUsername(user.get().getUsername());
            if (byUsernameOptional.isEmpty()) {
                return "redirect:login_page?username=non-exist";
            }
            if (!encoder.matches(user.get().getPassword(), byUsernameOptional.get().getPassword())) {
                return "redirect:login_page?password=no-match";
            }
            User userFromOptional = byUsernameOptional.get();
            final User userToSession = new User(userFromOptional.getId(), userFromOptional.getName(), userFromOptional.getSurname(), userFromOptional.getUsername(), userFromOptional.getEmail(), userFromOptional.getType(), userFromOptional.getEnabled());
            session.setAttribute("user", userToSession);
            return "redirect:blog?login=success";
        }
        return "redirect:login_page?login=error";
    }

    @PostMapping(path = "/blog/add_post")
    public String add_post(@ModelAttribute Optional<Post> post, HttpSession session){
        if (post.isPresent()){
            Post postPresent = post.get();
            if (postPresent.getHeader().trim().equals("")){
                return "redirect:/blog?header=emtpy";
            }
            if (postPresent.getText().trim().equals("")){
                return "redirect:/blog?text=empty";
            }
            final User userFromSession = (User) session.getAttribute("user");
            if (userFromSession == null){
                return "redirect:/blog?user=unauthorized";
            }
            final Post postToSave = new Post(postPresent.getHeader(), postPresent.getText(), userFromSession.getId());
            postService.createPost(postToSave);
            return "redirect:/blog?status=post_created";
        }
        return "redirect:/blog?status=error";
    }

}
