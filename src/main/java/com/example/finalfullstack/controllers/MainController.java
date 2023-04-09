package com.example.finalfullstack.controllers;

import com.example.finalfullstack.models.Person;
import com.example.finalfullstack.security.PersonDetails;
import com.example.finalfullstack.services.PersonService;
import com.example.finalfullstack.services.ProductService;
import com.example.finalfullstack.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final PersonValidator personValidator;
    private final PersonService personService;
    private final ProductService productService;

    public MainController(PersonValidator personValidator, PersonService personService, ProductService productService) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.productService = productService;
    }

    @GetMapping("/my")
    public String index(Model model){
        // объект аутентификации -> обращаемся к контексту и на нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String role = personDetails.getPerson().getRole();
        if (role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        model.addAttribute("products", productService.getAllProduct());
        return "user/index";

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
    @GetMapping("/my/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "user/info_product";
    }
}
