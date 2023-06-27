package com.example.techconnect.controllers;
import com.example.techconnect.models.User;
import com.example.techconnect.models.Event;
import com.example.techconnect.repositories.EventRepository;
import com.example.techconnect.repositories.UserRepository;
import org.springframework.data.repository.query.Param;
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

    public RestApiController(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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



}
