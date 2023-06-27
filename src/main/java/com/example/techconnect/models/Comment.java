package com.example.techconnect.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String title;

    @JsonProperty("content")
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String content;

    @JsonProperty("createdAt")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Entity Relationships

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "discussion_id")
    private Discussion discussion;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;




}
