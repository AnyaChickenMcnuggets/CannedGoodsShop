package com.example.finalfullstack.config;

import com.example.finalfullstack.services.PersonDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    private final PersonDetailsService personDetailsService;

    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

//    private final AuthenticationProvider authenticationProvider;

//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        authenticationManagerBuilder.userDetailsService(personDetailsService);
    }
}
