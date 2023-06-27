package com.example.techconnect.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discussions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discussion {


    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    @Column(nullable = false)
    private String title;
    @JsonProperty("content")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @JsonProperty("createdAt")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Relationship Entity


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "discussion",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();





}
