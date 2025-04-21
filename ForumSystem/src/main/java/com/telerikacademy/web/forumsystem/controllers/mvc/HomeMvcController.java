package com.telerikacademy.web.forumsystem.controllers.mvc;

import com.telerikacademy.web.forumsystem.exceptions.AuthenticationFailureException;
import com.telerikacademy.web.forumsystem.helpers.AuthenticationHelper;
import com.telerikacademy.web.forumsystem.models.*;
import com.telerikacademy.web.forumsystem.services.PostService;
import com.telerikacademy.web.forumsystem.services.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.telerikacademy.web.forumsystem.models.FilterPostDTO.checkIfFilterIsEmpty;
import static com.telerikacademy.web.forumsystem.models.FilterUserDTO.checkIfUserFilterIsEmpty;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final PostService postService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    public HomeMvcController(PostService postService, UserService userService, AuthenticationHelper authenticationHelper) {
        this.postService = postService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        model.addAttribute("mostRecentPosts", postService.getMostRecentPosts());
        model.addAttribute("mostCommentedPosts", postService.getMostCommentedPosts());
        model.addAttribute("userCount", userService.getUserCount());
        model.addAttribute("postCount", postService.getPostCount());
        if (populateIsAuthenticated(session)) {
            String currentUsername = (String) session.getAttribute("currentUser");
            model.addAttribute("currentUser", userService.getByUsername(currentUsername));
        }
        return "HomeView";
    }



    @GetMapping("/about")
    public String showAboutPage() {
        return "About";
    }

}
