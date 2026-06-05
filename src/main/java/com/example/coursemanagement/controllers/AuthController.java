package com.example.coursemanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
