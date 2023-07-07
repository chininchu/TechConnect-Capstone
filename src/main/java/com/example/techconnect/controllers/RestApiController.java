package com.example.techconnect.controllers;
import com.example.techconnect.models.Discussion;
import com.example.techconnect.models.Review;
import com.example.techconnect.models.User;
import com.example.techconnect.models.Event;
import com.example.techconnect.repositories.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/events",headers = "Accept=application/json")
public class RestApiController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final InterestRepository interestRepository;
    private final DiscussionRepository discussionRepository;

    private final ReviewRepository reviewRepository;
    public RestApiController(EventRepository eventRepository, UserRepository userRepository, InterestRepository interestRepository, DiscussionRepository discussionRepository, ReviewRepository reviewRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
        this.reviewRepository = reviewRepository;
        this.discussionRepository = discussionRepository;
    }

    @GetMapping("/allEvents")
    public List <Event> allEvents(){
        return eventRepository.findAll();
    }

    @GetMapping("/userEvents")
    public List <Event> userEvents(){
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return eventRepository.findAllByHostId(loggedIn.getId());
    }

    @GetMapping("/searchEvents")
    public List <Event> searchEvents(@RequestParam (name = "location")String location){
        System.out.println(eventRepository.findEventByLocation(location));
        return eventRepository.findEventByLocation(location);
    }
    @GetMapping("/searchInterest")
    public List <Event> searchByInterest(@RequestParam (name = "interest") long interestId,Model model){
        System.out.println(eventRepository.findAllByInterest_Id(interestId));
        return eventRepository.findAllByInterest_Id(interestId);
    }


    @GetMapping("/searchKeyword")
    public List <Event> searchByKeyWord(@RequestParam (name = "keyword") String keyword){
        return eventRepository.findEventByTitleContainingIgnoreCase(keyword);
    }
    @GetMapping("/closestEvents")
    public List <Event> findClosestEvents(){
        return eventRepository.findAllByDateTimeGreaterThan(LocalDateTime.now());
    }










}
