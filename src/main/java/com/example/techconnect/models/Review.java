package com.example.techconnect.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "review")
public class Review {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String title;

    @JsonProperty("description")
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String description;

    @JsonProperty("rating")
    @Column(nullable = false, columnDefinition = "INT")
    private int rating;


    // Create Relationships

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


}
