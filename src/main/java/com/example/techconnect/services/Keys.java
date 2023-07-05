package com.example.techconnect.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class Keys {

    @Value("${MAPBOXAP_TOK}")
    private String SECOND_KEY;

    public String getSECOND_KEY() {
        return SECOND_KEY;
    }
    private void setSECOND_KEY(String SECOND_KEY) {
        this.SECOND_KEY = SECOND_KEY;
    }
}