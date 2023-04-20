package com.example.finalfullstack.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    @GetMapping("/authentication")
    public String login(){
        return "authentication";
    }

}
