package com.example.finalfullstack.util;

import com.example.finalfullstack.models.Person;
import com.example.finalfullstack.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    // для какой модели
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    // сама валидация
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.findByLogin(person) != null){
            errors.rejectValue("login", "", "Пользователь с указанным логином уже зарегистрирован");
        }
    }
}
