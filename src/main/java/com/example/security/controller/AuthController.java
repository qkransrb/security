package com.example.security.controller;

import com.example.security.dto.auth.SignUpRequest;
import com.example.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signup")
    public String getSignUp() {
        return "page/auth/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute SignUpRequest signUpRequest) {
        authService.signup(signUpRequest);
        return "redirect:/auth/signin";
    }

    @GetMapping("/signin")
    public String getSignIn() {
        return "page/auth/signin";
    }
}
