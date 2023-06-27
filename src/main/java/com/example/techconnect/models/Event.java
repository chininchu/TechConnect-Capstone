package com.example.techconnect.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


@Entity
@JsonRootName("events")
@Table(name = "events")

public class Event {


    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EventId;

    @JsonProperty("title")
    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String title;

    @JsonProperty("description")
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String description;


    @JsonProperty("location")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String location;

    @JsonProperty("dataTime")
    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;

    // Entity Relationship diagram


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="host_id")

    // This is now considered to bes host and not user
    private User host;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="interest_id")
    private Interest interest;


    @JsonManagedReference
    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private List<Attendee>attendees = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private List<Review>reviews = new ArrayList<>();









}
