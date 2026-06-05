package com.example.coursemanagement.config;

import com.example.coursemanagement.services.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public final CustomAuthenticationProvider cap;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/**").permitAll()
                        .requestMatchers("/courses/create").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form->form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login_submit")
                        .defaultSuccessUrl("/courses", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        http.authenticationProvider(cap);
        return http.build();
    }
}
