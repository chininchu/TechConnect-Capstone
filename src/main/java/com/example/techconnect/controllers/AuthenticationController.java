package com.example.techconnect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/LoginPage")
    public String showLoginForm() {
        return "LoginPage";
    }



}