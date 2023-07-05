package com.example.techconnect.controllers;

import com.example.techconnect.services.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class KeyController {

        @Autowired
        private Keys keys;

        @GetMapping(value="/keys.js", produces = "application/javascript")
        public String getKeys() {
            return String.format("""
                   const GITHUBTOK = "%s";
                   const MAPBOXAP_TOK = "%s";
//                   """, keys.getTEST_KEY(), keys.getSECOND_KEY());
        }
    }