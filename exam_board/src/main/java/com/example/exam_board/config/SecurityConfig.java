package com.example.exam_board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 컨트롤러에서 인증되지않은 사용자가 특정 컨트롤러에 접근할때 인증을 먼저 받고올 수 있도록....하는..기능.....
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((request)-> request
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/articles/lists", "/user/**").permitAll()
                        .anyRequest().authenticated())

                .formLogin((form)->form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("userId")
                        .defaultSuccessUrl("/articles/lists", true))

                .logout(out->out
                        .logoutSuccessUrl("/articles/lists")
                        .logoutUrl("/logout"))

                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
