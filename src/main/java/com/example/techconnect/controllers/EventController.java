package com.example.techconnect.controllers;

import com.example.techconnect.models.Event;

import com.example.techconnect.models.Interest;
import com.example.techconnect.models.User;
import com.example.techconnect.repositories.EventRepository;
import com.example.techconnect.repositories.InterestRepository;
import com.example.techconnect.repositories.ReviewRepository;
import com.example.techconnect.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@Controller
public class EventController {

    private final EventRepository eventRepository;

//    private final AddressUtility addressUtility;

    private final UserRepository userRepository;

    private final InterestRepository interestRepository;

    private final ReviewRepository reviewRepository;


    public EventController(EventRepository eventRepository, UserRepository userRepository, InterestRepository interestRepository,ReviewRepository reviewRepository) {

        this.eventRepository = eventRepository;
//      this.addressUtility = addressUtility;
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
        this.reviewRepository = reviewRepository;


    }

    @GetMapping("/events/ajax")
    public String viewAllEventsWithAjax() {
        return "/apitester";
    }



    // I want to login to the website

    // The page should be displayed to the user


//    @GetMapping("/event/create")
//    public String showEventForm(Model model) {
//
//        model.addAttribute("event", new Event());
//
//        return "/event/create";
//    }
//
//    @PostMapping("/event/create")
//
//    public String createEvent(@ModelAttribute Event event) {
//
//        eventRepository.save(event);
//
//        return "/event/create";
//
//
//    }



    // We need the user's session key from when they login


    // I want to click a button that creates an event


    // We need a form that has a POST method
    // @Get will simply render the page

    // Post method grabs the registration information

    // Use the Address utility method to store Street, city, state information

    // Creates a new Event Object

    // Saves it to the DB

//    <!--The naming convention has been changed from /event to /event/create-->

    @GetMapping("/event/create")
    public String showEventForm(Model model) {


        model.addAttribute("interests", interestRepository.findAll());


        model.addAttribute("event", new Event());

        return "/event/create";
    }

    @PostMapping("/event/create")

    public String createEvent(@ModelAttribute Event event) {


        // This piece of code allows us to access the authenticated User;

        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // We are setting the whole User Object to the Event table and JPA will only save the Id in the whole database

        event.setHost(loggedIn);


        eventRepository.save(event);




        return "redirect:/profile";


    }


    // Create a method that will edit events

    // ALl new mappings within Controllers need to be added to the Security Configuration Class

    @GetMapping("/event/{eventId}/edit")
    public String showEditEventPage(@PathVariable long eventId, Model model) {

        Event event = eventRepository.findById(eventId).get();
        model.addAttribute("event", event);

        return "event/edit";

    }


    @PostMapping("/event/{eventId}/edit")
    public String editEvents(@ModelAttribute Event event, @PathVariable long eventId) {


        // Update the event with the form data

        event.setHost(event.getHost());
        event.setInterest(event.getInterest());
        event.setEventId(eventId);
        event.setTitle(event.getTitle());
        event.setDateTime(event.getDateTime());
        event.setDescription(event.getDescription());
        event.setLocation(event.getLocation());


        eventRepository.save(event);


        return "redirect:/profile";
    }


    @PostMapping("/event/{eventId}/delete")

    public String deleteEvent(@ModelAttribute Event event, @PathVariable long eventId) {

        event.setHost(new User());
        event.setInterest(new Interest());
        eventRepository.deleteById(eventId);
        return "redirect:/profile";


    }

    @GetMapping("/event/reviews/{eventId}")
    public String showEventReviews(@PathVariable long eventId, Model model) {
        // Retrieve the reviews for the specified event from the database

        model.addAttribute("eventId", eventRepository.findById(eventId).get());
        model.addAttribute("reviews", reviewRepository.findAllByEventId(eventId));
        return "event-reviews";


    }






}
