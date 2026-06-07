package com.example.coursemanagement.controllers;

import com.example.coursemanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserService us;

    @GetMapping()
    public String loadRegisterPage() {
        return "auth/Register";
    }

    @GetMapping("login")
    public String loadLoginPage() {
        return "auth/Login";
    }

    @PostMapping("register")
    public String registerUser(
            @Valid
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        //Validate
        if (us.findByUsername(username) != null) {
            model.addAttribute("error", "Tên người dùng đã được sử dụng");
            return "auth/Register";
        }

        try {
            us.registerUser(
                    username,
                    password,
                    fullName,
                    email
            );

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Đăng ký thành công. Vui lòng đăng nhập lại"
            );

            return "redirect:/auth/login";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/Register";
        }
    }
}
