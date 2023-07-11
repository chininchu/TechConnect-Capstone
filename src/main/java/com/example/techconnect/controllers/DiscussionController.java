package com.example.techconnect.controllers;


import com.example.techconnect.models.*;
import com.example.techconnect.repositories.CommentRepository;
import com.example.techconnect.repositories.DiscussionRepository;
import com.example.techconnect.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class DiscussionController {
    private final DiscussionRepository discussionRepository;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public DiscussionController(DiscussionRepository discussionRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.discussionRepository = discussionRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }
//INITIAL PAGE VIEW ALL DISCUSSION
    @GetMapping("/discussions")
    public String showDiscussions(Model model, Principal principal) {
//        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the user is logged in
        boolean isLoggedIn = principal != null;

        // Pass the login status to the template
        model.addAttribute("isLoggedIn", isLoggedIn);

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("loggedInUser", null);

        } else {
            User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("loggedInUser", loggedIn.getId());

        }
        model.addAttribute("discussions", discussionRepository.findAll());
        List<Discussion> discussions = discussionRepository.findAll();
        discussions.sort(Comparator.comparing(Discussion::getCreatedAt).reversed());
        model.addAttribute("discussions1", discussions);
        model.addAttribute("comment", new Comment());
        model.addAttribute("discussion", new Discussion());
        return "discussions-test";
    }
//DELETE DISCUSSIONS

    @PostMapping("/discussions/{id}/delete")
    public String deleteDiscussion(@ModelAttribute Discussion discussion, @PathVariable long id) {
        discussion.setUser(new User());
        discussion.setComments(new ArrayList<>());
        discussionRepository.deleteById(id);
        return "redirect:/discussions";


    }

//    COMMENT ON A DISCUSSION
    @GetMapping("/discussion/{id}/comment")
    public String showCommentForm(@PathVariable Long id, Model model) {
        Discussion discussionId = discussionRepository.findById(id).get();
        System.out.println("line 54: " + discussionId.getId());
        model.addAttribute("comment", new Comment());
        return "discussions-test";
    }

//POSTMAPPING COMMENT ON DISCUSSION
    @PostMapping("/discussion/{id}/comment")
    public String createComment(@PathVariable Long id, @ModelAttribute Comment comment){
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Discussion currentDisc = discussionRepository.findById(id).get();


        comment.setUser(loggedIn);
        comment.setDiscussion(currentDisc);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);



        return "redirect:/discussions";

    }
//DELETE YOUR COMMENT
    @PostMapping("/comments/{id}/delete")
    public String deleteComment(@ModelAttribute Comment comment, @PathVariable long id) {
        comment.setUser(new User());
        comment.setDiscussion(new Discussion());
        commentRepository.deleteById(id);
        return "redirect:/discussions";
    }

//    POST A DISCUSSION
    @PostMapping("/discussion/create")
    public String createDiscussion(@ModelAttribute Discussion discussion,Model model) {

        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userRepository.findById(loggedIn.getId()).get());


        discussion.setUser(loggedIn);
        discussion.setCreatedAt(LocalDateTime.now());
        discussionRepository.save(discussion);


        return "redirect:/discussions";

    }




//EDIT YOUR DISCUSSION

    @GetMapping("/discussion/{id}/edit")
    public String showEditDiscussionPage(@PathVariable long id, Model model) {
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Discussion discussion = discussionRepository.findById(id).get();
        System.out.println(discussion);
        model.addAttribute("discussion", discussion);


        return "discussions-test";

    }

//    POST EDIT DISCUSSION

    @PostMapping("/discussion/{id}/edit")
    public String editDiscussion(@PathVariable long id,@ModelAttribute Discussion discussion,Model model,@ModelAttribute User user) {
        Discussion discussion1 = discussionRepository.findById(id).get();
        model.addAttribute("discussion", discussion1);

//          discussion1.setUser(discussion.getUser());
            discussion1.setId(id);
            discussion1.setTitle(discussion.getTitle());
            discussion1.setCreatedAt(LocalDateTime.now());
            discussion1.setContent(discussion.getContent());


            discussionRepository.save(discussion1);

            return "redirect:/discussions";

        }

    }
