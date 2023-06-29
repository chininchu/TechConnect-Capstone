package com.example.techconnect.controllers;
import com.example.techconnect.models.User;
import com.example.techconnect.repositories.EventRepository;
import com.example.techconnect.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder encoder;

//    @Value("file-upload-path")
//    private String uploadPath;

    private EventRepository eventRepository;
    public UserController(UserRepository userDao, PasswordEncoder encoder, EventRepository eventRepository) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.eventRepository = eventRepository;
    }
    @GetMapping("/SignUpPage")
    public String showSignupForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/SignUpPage";
    }
    @PostMapping("/SignUpPage")
    public String registerUser(@ModelAttribute User user, Model model,@RequestParam(name = "profilePicture") String profilePicture) {
        // Hash the password
        String hash = encoder.encode(user.getPassword());
        // Set the hashed password BEFORE saving to the database
        user.setPassword(hash);
        // Save the profile picture to the database or perform any necessary operations
//        String fileUrl = saveProfilePictureToDatabase(profilePicture);
//        user.setProfilePicture(fileUrl);
        // The code below grabs the attribute name from the @RequestParam and sets it to the profile picture.
        user.setProfilePicture(profilePicture);
        // Set the user attribute in the session
//        request.getSession().setAttribute("user", user);
        model.addAttribute("user", user);
        userDao.save(user);
        return "redirect:/profile";
    }



    private String saveProfilePictureToDatabase(MultipartFile profilePicture) {
        // Implement the logic to save the profile picture to the database
        // Here, you can use your preferred method or framework to store the file and return the URL or identifier of the saved picture
        // Example implementation using Spring Boot’s MultipartFile.transferTo() method:
        String fileUrl = null;
        try {
            // Specify the directory path where you want to save the profile pictures
            String directoryPath = "/Users/coleusher/IdeaProjects/TechConnect/src/main/resources/static/images";
            // Generate a unique file name or use the original file name
            String fileName = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
            // Create a Path object with the directory path and file name
            Path filePath = Paths.get(directoryPath, fileName);
            // Transfer the profile picture file to the specified location
            profilePicture.transferTo(filePath.toFile());
            // Set the file URL as the saved file’s path or identifier
            fileUrl = filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception or return an error message if the file saving fails
        }
        return fileUrl;
    }

    @PostMapping("/LoginPage")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        // Retrieve the user object from the database based on the provided username
        User authenticatedUser = userDao.findByUsername(user.getUsername());
        System.out.println("Username:" + authenticatedUser);
        // Check if the user exists and the password matches
        if (authenticatedUser != null && encoder.matches(user.getPassword(), authenticatedUser.getPassword())) {
            // Authentication successful, set the user attribute in the session
            request.getSession().setAttribute("user", authenticatedUser);
            return "redirect:/profile";
        }
        // if Authentication failed, redirect back to the login page with an error message
        return "redirect:LoginPage";
    }



    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/LoginPage";
    }
    @GetMapping("/profile")
    public String showProfile(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("user", loggedInUser);
        model.addAttribute("user",userDao.findById(loggedInUser.getId()).get());

        // This code shows the event to the user. Please don't delete this code. Consult with Andrew Chu
        model.addAttribute("events", eventRepository.findAllByHostId(loggedInUser.getId()));
        return "/profile";
    }


    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User user, Model model) {
        // Retrieve the user object from the database based on the provided username
        User authenticatedUser = userDao.findByUsername(user.getUsername());
        // Check if the user exists and the password matches
        if (authenticatedUser != null && encoder.matches(user.getPassword(), authenticatedUser.getPassword())) {
            // Authentication successful, set the user attribute in the session
            model.addAttribute("user", authenticatedUser);
            return "/profile";
        }
        // if Authentication failed, redirect back to the login page with an error message
        return "redirect:/LoginPage?error";
    }



    @GetMapping("/event/{id}/editProfile")
    public String showEditProfileForm(@PathVariable long id,Model model) {
         User user = userDao.findById(id).get();
         model.addAttribute("user", user);
         return "/editProfile";
    }




//    private boolean validatePassword(String password) {
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
    @PostMapping("/event/{id}/editProfile")
    public String editProfile(@ModelAttribute User user,@PathVariable long id,@RequestParam(name = "profilePicture") String profilePicture) {
        user.setEmail(user.getEmail());
        user.setId(id);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUsername(user.getUsername());
        user.setProfilePicture(user.getProfilePicture());
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
            return "redirect:/profile";

    }



    @PostMapping("/deleteProfile")
    public String deleteProfile() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User deletedUser = userDao.findById(loggedInUser.getId()).get();
        userDao.delete(deletedUser);
        return "redirect:/LoginPage";
    }
}