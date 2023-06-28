//package com.example.techconnect.utilities;
//
//public class passwordValidity {
//    private String password;
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public passwordValidity(String password){
//        this.password= password;
//    }
//
//    private String validatePassword(String password) {
//        if (password.length() < 4) {
//            return "Password must be at least 4 characters long.";
//        }
//
//        if (!password.matches(".*\\d.*")) {
//            return "Password must contain at least one digit.";
//        }
//
//        if (!password.matches(".*[A-Z].*")) {
//            return "Password must contain at least one uppercase letter.";
//        }
//
//        if (!password.matches(".*[@#$%^&+=].*")) {
//            return "Password must contain at least one special character.";
//        }
//
//        return "";
//    }
//
//
//}
