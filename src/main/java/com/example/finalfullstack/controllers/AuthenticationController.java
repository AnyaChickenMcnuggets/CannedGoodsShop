package com.example.finalfullstack.controllers;


import com.example.finalfullstack.models.Person;
import com.example.finalfullstack.services.PersonService;
import com.example.finalfullstack.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    private final PersonValidator personValidator;
    private final PersonService personService;


    public AuthenticationController(PersonValidator personValidator, PersonService personService) {
        this.personValidator = personValidator;
        this.personService = personService;
    }

    @GetMapping("/authentication")
    public String login(){
        return "authentication";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person){
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "registration";
        }
        personService.register(person);
        return "redirect:/my";
    }

    @GetMapping("/admin/registration")
    public String registrationAdmin(@ModelAttribute("person") Person person){
        return "admin/registration";
    }

    @PostMapping("/admin/registration")
    public String resultRegistrationAdmin(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "admin/registration";
        }
        personService.register(person);
        return "redirect:/admin/person";
    }

}
