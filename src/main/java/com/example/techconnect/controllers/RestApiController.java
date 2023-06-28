package com.example.techconnect.controllers;
import com.example.techconnect.models.Interest;
import com.example.techconnect.models.User;
import com.example.techconnect.models.Event;
import com.example.techconnect.repositories.EventRepository;
import com.example.techconnect.repositories.InterestRepository;
import com.example.techconnect.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/events",headers = "Accept=application/json")
public class RestApiController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final InterestRepository interestRepository;
    public RestApiController(EventRepository eventRepository, UserRepository userRepository, InterestRepository interestRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
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




}
