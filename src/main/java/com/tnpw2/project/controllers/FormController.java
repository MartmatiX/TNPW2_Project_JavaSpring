package com.tnpw2.project.controllers;

import com.tnpw2.project.database_operations.PostService;
import com.tnpw2.project.database_operations.UserService;
import com.tnpw2.project.post_objects.Post;
import com.tnpw2.project.user_objects.Type;
import com.tnpw2.project.user_objects.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
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
    public String add_post(@ModelAttribute Optional<Post> post, HttpSession session) {
        if (post.isPresent()) {
            Post postPresent = post.get();
            if (postPresent.getHeader().trim().equals("")) {
                return "redirect:/blog?header=emtpy";
            }
            if (postPresent.getText().trim().equals("")) {
                return "redirect:/blog?text=empty";
            }
            final User userFromSession = (User) session.getAttribute("user");
            if (userFromSession == null) {
                return "redirect:/blog?user=unauthorized";
            }
            final Post postToSave = new Post(postPresent.getHeader(), postPresent.getText(), userFromSession.getId());
            postService.createPost(postToSave);
            return "redirect:/blog?status=post_created";
        }
        return "redirect:/blog?status=error";
    }

    @Transactional
    @PostMapping("/blog/update_post")
    public String update_post(@ModelAttribute Optional<Post> post, HttpSession session) {
        if (post.isPresent()) {
            if (validateUserSession(session)) {
                Post postFromOptional = post.get();
                if (postFromOptional.getText().trim().equals("") || postFromOptional.getText().isEmpty()) {
                    return "redirect:/blog/my_posts/" + postFromOptional.getId().toString() + "?text=empty";
                }
                if (postFromOptional.getHeader().trim().equals("") || postFromOptional.getHeader().isEmpty()) {
                    return "redirect:/blog/my_posts/" + postFromOptional.getId().toString() + "?header=empty";
                }
                if (!isUsersPost(session, postFromOptional.getId())) {
                    return "redirect:/blog/my_posts?post=notYours";
                }
                if (postService.updatePost(postFromOptional.getHeader(), postFromOptional.getText(), postFromOptional.getId()) == 1) {
                    return "redirect:/blog/my_posts?post=updated";
                } else {
                    return "redirect:/blog/my_posts/" + postFromOptional.getId().toString() + "?status=serverError";
                }
            }
            return "redirect:/blog?status=error";
        }
        return "redirect:/blog?status=error";
    }

    @PostMapping("/blog/delete_post")
    public String delete_post(@ModelAttribute Optional<Post> post, HttpSession session) {
        if (post.isPresent()) {
            if (validateUserSession(session)) {
                Long id = post.get().getId();
                List<Post> individualPost = postService.getIndividualPost(id);
                if (individualPost.isEmpty()) {
                    return "redirect:/blog/my_posts?error=postNotFound";
                }
                Long userId = individualPost.get(0).getUser_id();
                User user = (User) session.getAttribute("user");
                if (!Objects.equals(userId, user.getId())) {
                    return "redirect:/blog/my_posts?error=notYourPost";
                }
                if (postService.deletePost(id)) {
                    return "redirect:/blog/my_posts?status=postDeleted";
                } else {
                    return "redirect:/blog/my_posts?status=databaseError";
                }
            }
        }
        return "redirect:/blog?status=error";
    }

    @PostMapping("/profile/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null){
            session.invalidate();
        }
        return "redirect:/login_page";
    }

    private boolean validateUserSession(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        User userFromSession = (User) session.getAttribute("user");
        return userService.findByUsername(userFromSession.getUsername()).isPresent();
    }

    private boolean isUsersPost(HttpSession session, Long post_id) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        User userFromSession = (User) session.getAttribute("user");
        List<Post> allUserPosts = postService.getAllUserPosts(userFromSession.getId());
        return allUserPosts.stream().anyMatch(p -> Objects.equals(p.getId(), post_id));
    }

}
