package com.example.techconnect.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonProperty("username")
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;


    @JsonProperty("email")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty("firstName")
    @Column(nullable = false)
    private String firstName;

    @JsonProperty("lastName")
    @Column(nullable = false)
    private String lastName;

    @JsonProperty("profilePic")
    @Column(nullable = false)
    private String profilePicture;


    // This is known as a copy constructor, which will make a clone of the user object.
    public User(User copy) {
        this.id = copy.id;
        this.email = copy.email;
        this.username = copy.username;
        this.password = copy.password;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.profilePicture = copy.profilePicture;
    }

    // Getter and Setter for profilePicture
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Relationship Entity

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<UserInterest> userInterests = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Discussion> discussions = new ArrayList<>();

//    @JsonManagedReference
//    @OneToMany(mappedBy = "user")
//    private List<Comment>comments = new ArrayList<>();
//
//
////    @OneToMany(mappedBy = "host")
//
//    @JsonManagedReference
//    @OneToMany(mappedBy = "user")
//
//    private List<Event> events = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();


    @JsonManagedReference
    @OneToMany(mappedBy = "host")
    private List<Event> events = new ArrayList<>();


}
