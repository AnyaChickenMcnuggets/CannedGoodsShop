package com.example.finalfullstack.config;

import com.example.finalfullstack.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.
//                jdbcAuthentication()
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(rolesQuery)
//                .dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // конфигурация работы Spring Security
        http.authorizeHttpRequests() //указываем что все страницы должны быть защищены аутентификацией
                .requestMatchers("/authentication", "/error", "/process_login", "/registration", "/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/product/**").permitAll() // указываем список общедоступных страниц без авторизации
                .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/authentication") // где формировать страницу аутентификации
                .loginProcessingUrl("/process_login") // куда отправляются данные с формы аутентификации (это базовый юрл, реализованный)
                .defaultSuccessUrl("/my/product", true) // куда отправляет при удачном входе
                .failureUrl("/authentication?error") // при неудачном сюда
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/product");
        return http.build();
    }


    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }
}
