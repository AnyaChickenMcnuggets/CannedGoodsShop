package com.example.finalfullstack.controllers;

import com.example.finalfullstack.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(){
        // объект аутентификации -> обращаемся к контексту и на нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        System.out.println("ID : " + personDetails.getPerson().getId());
        System.out.println("login : " + personDetails.getPerson().getLogin());
        System.out.println("password : " + personDetails.getPerson().getPassword());

        return "index";
    }
}
