package com.tnpw2.project.controllers;

import com.tnpw2.project.database_operations.UserService;
import com.tnpw2.project.user_objects.Type;
import com.tnpw2.project.user_objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    private final UserService userService;

    @Autowired
    public FormController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/validate_registration")
    public String validate_registration(@ModelAttribute User formUser){
        User user = new User(formUser.getId(), formUser.getName(), formUser.getSurname(), formUser.getUsername(), formUser.getEmail(), formUser.getPassword(), Type.User);
        if (userService.registerUser(user)){
            return "redirect:index?registration=successful";
        }
        return "redirect:index?registration=failure";
    }

}
