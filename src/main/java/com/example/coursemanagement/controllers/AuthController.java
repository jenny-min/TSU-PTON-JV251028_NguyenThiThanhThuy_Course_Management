package com.example.coursemanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    @GetMapping()
    public String loadRegisterPage() {
        return "auth/Register";
    }

    @GetMapping("login")
    public String loadLoginPage() {
        return "auth/Login";
    }
}
