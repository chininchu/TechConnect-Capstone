package com.example.techconnect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class AboutUs {
    @Controller
    public static class getAboutUspage {

        @GetMapping("/AboutUs")
        public String showLoginForm() {
            return "AboutUs";
        }



    }

}
