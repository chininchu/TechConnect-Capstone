package com.example.techconnect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public class AboutUs {
    @Controller
    public static class getAboutUspage {

        @GetMapping("/AboutUs")
        public String showLoginForm(Principal principal, Model model) {
            // Check if the user is logged in
            boolean isLoggedIn = principal != null;

            // Pass the login status to the template
            model.addAttribute("isLoggedIn", isLoggedIn);

            return "AboutUs";
        }



    }

}
