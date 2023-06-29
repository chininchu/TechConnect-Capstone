package com.example.techconnect.utilities;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$";

    public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }


}
