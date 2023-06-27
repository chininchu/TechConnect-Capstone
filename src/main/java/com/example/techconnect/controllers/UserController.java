package com.example.techconnect.controllers;
import com.example.techconnect.models.User;
import com.example.techconnect.repositories.EventRepository;
import com.example.techconnect.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder encoder;
//    @Value("file-upload-path")
//    private String uploadPath;

    private EventRepository eventRepository;
    public UserController(UserRepository userDao, PasswordEncoder encoder,EventRepository eventRepository) {
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
    public String registerUser(
            @ModelAttribute User user,
            Model model,@RequestParam(name = "profilePicture") String profilePicture
    ) {
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
    //  @GetMapping(“/user.json”)
//  public @ResponseBody List<User> viewUsersInJson(){
//     return userDao.findAll();
//  }
//  @GetMapping(“/users/ajax”)
//  public String viewAllUsersWithAjax() {
//    return “users/ajax”;
//  }
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
    @GetMapping("/editProfile")
    public String showEditProfileForm(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", loggedInUser);
        return "editProfile"; // Return the name of the template
    }
    @PostMapping("/editProfile")
    public String editProfile(@ModelAttribute User user, Model model, @RequestParam(name = "image-upload") MultipartFile profilePicture) {
        // Retrieve the currently logged-in user
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User editedUser = userDao.findById(loggedInUser.getId()).get();
        // Update the relevant fields of the logged-in user with the new information
        editedUser.setEmail(user.getEmail());
        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());
        editedUser.setUsername(user.getUsername());
        editedUser.setPassword(user.getPassword());
        editedUser.setProfilePicture(user.getProfilePicture());
        // Check if the provided password matches the user’s current password
        if (encoder.matches(user.getPassword(), editedUser.getPassword())) {
            // Save the updated user to the database
            userDao.save(editedUser);
            model.addAttribute("user", editedUser);
            return "redirect:/profile";
        }
        // If the provided password doesn’t match, redirect back to the profile page with an error message
        return "redirect:/profile?error";
    }
    @PostMapping("/deleteProfile")
    public String deleteProfile() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User deletedUser = userDao.findById(loggedInUser.getId()).get();
        // Perform the deletion operation on the user’s profile using the userRepository
        userDao.delete(deletedUser);
        // Redirect to a different page after the deletion, e.g., the homepage
        return "redirect:/LoginPage";
    }
}