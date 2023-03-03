package com.tnpw2.project.controllers;

import com.tnpw2.project.user_objects.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GuiController {

    @GetMapping
    public String initStartupPage(){
        return "permit_all/index";
    }

    @GetMapping(path = "/index")
    public String initIndexPage(){
        return "permit_all/index";
    }

    @GetMapping(path = "/register_page")
    public String initRegisterPage(){
        return "permit_all/register_page";
    }

    @GetMapping(path = "/login_page")
    public String initLoginPage(){
        return "permit_all/login_page";
    }

    @GetMapping(path = "/blog")
    public String initBlogPage(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "permit_logged/blog_page";
    }
}
