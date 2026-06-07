package com.example.coursemanagement.config;

import com.example.coursemanagement.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public final CustomUserDetailsService userDetailsService;

    //Bean tự động mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/**").permitAll()
                        .requestMatchers(//chỉ admin
                                "/courses/create", //đăng ký
                                "/courses/delete/**",//xóa
                                "/courses/update/**",//cập nhật
                                "/courses/save"//lưu
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form->form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login_submit")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/courses");
                        })
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        http.authenticationProvider(authenticationProvider);
        return http.build();
    }
}
