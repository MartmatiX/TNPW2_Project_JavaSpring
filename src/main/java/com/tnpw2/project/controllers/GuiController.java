package com.tnpw2.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GuiController {

    @GetMapping
    public String initIndexPage(){
        return "permit_all/index";
    }

    @GetMapping("/register_page")
    public String initRegisterPage(){
        return "permit_all/register_page";
    }

    @GetMapping("/login_page")
    public String initLoginPage(){
        return "permit_all/login_page";
    }

    @GetMapping("/blog")
    public String initBlogPage(){
        return "permit_logged/blog_page";
    }
}
