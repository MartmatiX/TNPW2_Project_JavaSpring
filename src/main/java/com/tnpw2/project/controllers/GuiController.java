package com.tnpw2.project.controllers;

import com.tnpw2.project.database_operations.PostService;
import com.tnpw2.project.database_operations.UserService;
import com.tnpw2.project.post_objects.Post;
import com.tnpw2.project.user_objects.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class GuiController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public GuiController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public String initStartupPage() {
        return "permit_all/index";
    }

    @GetMapping(path = "/index")
    public String initIndexPage() {
        return "permit_all/index";
    }

    @GetMapping(path = "/register_page")
    public String initRegisterPage(HttpSession session) {
        if (alreadyAuthorized(session)) {
            return "redirect:/profile?status=authorized";
        }
        return "permit_all/register_page";
    }

    @GetMapping(path = "/login_page")
    public String initLoginPage(HttpSession session) {
        if (alreadyAuthorized(session)) {
            return "redirect:/profile?status=authorized";
        }
        return "permit_all/login_page";
    }

    @GetMapping(path = "/profile")
    public String initUserProfile(Model model, HttpSession session) {
        if (validateUserSession(session)) {
            final User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
            return "permit_logged/user_page";
        }
        return "redirect:/login_page?user=error";
    }

    @GetMapping(path = "/blog")
    public String initBlogPage(Model model, HttpSession session) {
        if (validateUserSession(session)) {
            final User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
            return "permit_logged/blog_page";
        }
        return "redirect:/login_page?user=error";
    }

    @GetMapping(path = "/blog/posts")
    public String initBlogPostsPage(Model model, HttpSession session) {
        if (validateUserSession(session)) {
            List<Post> allPosts = postService.getAllPosts();
            model.addAttribute("posts", allPosts);
            return "permit_logged/posts_page";
        }
        return "redirect:/login_page?user=error";
    }

    @GetMapping(path = "/blog/create_post")
    public String initCreatePostPage(HttpSession session) {
        if (validateUserSession(session)) {
            return "permit_logged/create_post_page";
        }
        return "redirect:/login_page?user=error";
    }

    @GetMapping(path = "/blog/my_posts")
    public String initMyPostsPage(Model model, HttpSession session) {
        if (validateUserSession(session)) {
            User user = (User) session.getAttribute("user");
            List<Post> allUserPosts = postService.getAllUserPosts(user.getId());
            model.addAttribute("posts", allUserPosts);
            return "permit_logged/my_posts_page";
        }
        return "redirect:/login_page?user=error";
    }

    @GetMapping(path = "/blog/my_posts/{id}")
    public String initPostDetailsPage(Model model, HttpSession session, @PathVariable Long id) {
        if (validateUserSession(session)) {
            List<Post> individualPost = postService.getIndividualPost(id);
            if (individualPost.size() == 0) {
                return "redirect:/blog/my_posts?post=nonExistent";
            }
            if (individualPost.size() > 1) {
                return "redirect:/blog/my_posts?server=SQLError";
            }
            User user = (User) session.getAttribute("user");
            if (!Objects.equals(user.getId(), individualPost.get(0).getUser_id())) {
                return "redirect:/blog/my_posts?post=notYours";
            }
            model.addAttribute("post", individualPost.get(0));
            return "permit_logged/my_post_details_page";
        }
        return "redirect:/login_page?user=error";
    }

    private boolean validateUserSession(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        User userFromSession = (User) session.getAttribute("user");
        return userService.findByUsername(userFromSession.getUsername()).isPresent();
    }

    private boolean alreadyAuthorized(HttpSession session) {
        return session.getAttribute("user") != null;
    }

}
