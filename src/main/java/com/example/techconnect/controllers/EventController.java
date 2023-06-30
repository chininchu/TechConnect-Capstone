package com.example.techconnect.controllers;

import com.example.techconnect.models.*;

import com.example.techconnect.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class EventController {

    private final EventRepository eventRepository;

//    private final AddressUtility addressUtility;

    private final UserRepository userRepository;

    private final InterestRepository interestRepository;

    private final ReviewRepository reviewRepository;

    private final AttendeeRepository attendeeRepository;


    public EventController(EventRepository eventRepository, UserRepository userRepository, InterestRepository interestRepository, ReviewRepository reviewRepository, AttendeeRepository attendeeRepository) {

        this.eventRepository = eventRepository;
//      this.addressUtility = addressUtility;
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
        this.reviewRepository = reviewRepository;
        this.attendeeRepository = attendeeRepository;
    }

    @GetMapping("/events/ajax")
    public String viewAllEventsWithAjax(Model model) {
        model.addAttribute("interests", interestRepository.findAll());
        return "/apitester";
    }

    @GetMapping("/events/userpro")
    public String viewUserPro(Model model) {
        model.addAttribute("interests", interestRepository.findAll());
        return "/api_profile_test";
    }

    @GetMapping("/events/allevent")
    public String viewAllEvents(Model model) {
        model.addAttribute("interests", interestRepository.findAll());
        return "/api_eventsp_test";
    }



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


    //----------------------------------Review Entity -----------------------------------------------//


//----Review(Read)----//

    @GetMapping("/event/{eventId}/reviews")
    public String showEventReviews(@PathVariable long eventId, Model model) {

        Event event = eventRepository.findById(eventId).orElseThrow();
        List<Review> reviews = reviewRepository.findAllByEventId(eventId);
        double averageRating = calculateAverageRating(reviews);

        model.addAttribute("event", event);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("review", new Review());

        // Attendees Registration for an event
        List<Event> events = eventRepository.findAll();
        model.addAttribute("events", events);

        return "event-reviews";
    }

    @PostMapping("/event/{eventId}/reviews/create")
    public String saveReview(@PathVariable("eventId") long eventId, @ModelAttribute("review") Review review) {
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = eventRepository.findById(eventId).orElseThrow();

        review.setEvent(event);
        review.setUser(loggedIn);
        reviewRepository.save(review);

        return "redirect:/event/{eventId}/reviews";
    }

    private double calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRating();
        }

        return sum / reviews.size();
    }


    @GetMapping("/event/{eventId}/register")
    public String showRegisterForm(Model model, Attendee attendee, @PathVariable long eventId) {
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Event event = eventRepository.findById(eventId).orElseThrow(); // Retrieve the event object
        model.addAttribute("event", event); // Pass the event object to the model
        model.addAttribute("userId", loggedIn.getId());
        model.addAttribute("attendee", attendee);

        return "event-reviews";
    }


    @PostMapping("/event/{eventId}/register")
    public String registerForEvent(@PathVariable Long eventId, @ModelAttribute Attendee attendee) {
        // At this point, attendee should have its userId and eventId set,
        // thanks to Thymeleaf's form binding. You might want to perform additional
        // validation checks here, depending on your requirements.
        // Save the attendee to the database.

        attendeeRepository.save(attendee);
        // Redirect to a success page or back to the event page, or wherever is appropriate.
        // Here, we're redirecting to the event page.
        return "redirect:/event/{eventId}/reviews";
    }









    // The DeleteMapping method to delete the review from the database

    @PostMapping("/event/{eventId}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable("eventId") long eventId,
                               @PathVariable("reviewId") long reviewId, Model model) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));
        reviewRepository.delete(review);

        return "redirect:/event/{eventId}/reviews";
    }


    // Edit review

    @PostMapping("/event/{eventId}/reviews/{reviewId}/edit")
    public String editReview(@PathVariable Long eventId, @PathVariable Long reviewId,
                             @RequestParam("title") String title, @RequestParam("description") String description) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));
        review.setTitle(title);
        review.setDescription(description);
        reviewRepository.save(review);
        return "redirect:/event/{eventId}/reviews";
    }


}


















