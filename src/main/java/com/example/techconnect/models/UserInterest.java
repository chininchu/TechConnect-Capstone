package com.example.techconnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_interest")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInterest {


    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    // Entity Relationship

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="interest_id")
    private Interest interest;








}


