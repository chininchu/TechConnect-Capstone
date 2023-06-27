package com.example.techconnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "interests")


public class Interest {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("interest")
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String interest;

    // Entity Relationships

    @OneToMany(mappedBy = "interest")
    @JsonManagedReference
    private List<UserInterest> userInterests = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "interest")

    private List<Event> events = new ArrayList<>();


}
