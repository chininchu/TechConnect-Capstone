package com.example.techconnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


@Entity
@Table(name = "attendees")
public class Attendee {

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Entity Relationships

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "event_id")
    private Event event;


}
