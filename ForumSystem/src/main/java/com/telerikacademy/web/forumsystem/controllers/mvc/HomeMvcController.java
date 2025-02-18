package com.telerikacademy.web.forumsystem.controllers.mvc;

import com.telerikacademy.web.forumsystem.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final PostService postService;

    public HomeMvcController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("mostRecentPosts", postService.getMostRecentPosts());
        model.addAttribute("mostCommentedPosts", postService.getMostCommentedPosts());
        return "HomeView";
    }
}
