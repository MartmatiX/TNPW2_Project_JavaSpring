package com.tnpw2.project.controllers;

import jakarta.websocket.OnError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GuiController {

    @GetMapping
    public String initIndexPage(){
        return "index";
    }

    @GetMapping("/register_page")
    public String initRegisterPage(){
        return "register_page";
    }

    @GetMapping("/login_page")
    public String initLoginPage(){
        return "login_page";
    }
}
