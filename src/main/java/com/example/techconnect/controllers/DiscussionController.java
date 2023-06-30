package com.example.techconnect.controllers;


import com.example.techconnect.models.*;
import com.example.techconnect.repositories.CommentRepository;
import com.example.techconnect.repositories.DiscussionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;
import java.util.List;
@Controller
public class DiscussionController {
    private final DiscussionRepository discussionRepository;

    private final CommentRepository commentRepository;


    public DiscussionController(DiscussionRepository discussionRepository, CommentRepository commentRepository) {
        this.discussionRepository = discussionRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/discussions")
    public String showProfile(Model model) {
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("discussions", discussionRepository.findAll());
        model.addAttribute("loggedInUser", loggedIn.getId());
        //        model.addAttribute("loggedInUser", loggedIn);

        return "/discussions-test";
    }


    @PostMapping("/discussions/{id}/delete")
    public String deleteDiscussion(@ModelAttribute Discussion discussion, @PathVariable long id) {
        discussion.setUser(new User());
        discussion.setComments(new ArrayList<>());
        discussionRepository.deleteById(id);
        return "redirect:/discussions";


    }

    @GetMapping("/comments/create")
    public String showCommentForm(Model model) {
        model.addAttribute("comment", new Comment());
        return "/discussions-test";
    }



    @PostMapping("/comments/create")
    public String createComment(@ModelAttribute Comment comment){
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        comment.setUser(loggedIn);

        commentRepository.save(comment);

        return "redirect:/discussions";

    }

    @PostMapping("/comments/{id}/delete")
    public String deleteComment(@ModelAttribute Comment comment, @PathVariable long id) {
        comment.setUser(new User());
        comment.setDiscussion(new Discussion());
        commentRepository.deleteById(id);
        return "redirect:/discussions";
    }





    }
