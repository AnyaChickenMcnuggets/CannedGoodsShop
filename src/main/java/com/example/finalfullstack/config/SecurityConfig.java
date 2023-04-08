package com.example.finalfullstack.config;

import com.example.finalfullstack.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    private final PersonDetailsService personDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // конфигурация работы Spring Security
        http.csrf().disable() //отключаем защиту межсайтовой подделки запросов
                .authorizeHttpRequests() //указываем что все страницы должны быть защищены аутентификацией
                .requestMatchers("/authentication", "/error", "/registration").permitAll() // указываем список общедоступных страниц без авторизации
                .anyRequest().authenticated() // указываем что для всех остальных страниц необходима аутентификация
                .and()
                .formLogin().loginPage("/authentication") // где формировать страницу аутентификации
                .loginProcessingUrl("/process_login") // куда отправляются данные с формы аутентификации (это базовый юрл, реализованный)
                .defaultSuccessUrl("/", true) // куда отправляет при удачном входе
                .failureForwardUrl("/authentication?error") // при неудачном сюда
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/authentication");
        return http.build();
    }

    @Autowired
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
