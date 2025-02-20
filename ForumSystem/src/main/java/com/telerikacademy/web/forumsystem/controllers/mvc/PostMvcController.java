package com.telerikacademy.web.forumsystem.controllers.mvc;

import com.telerikacademy.web.forumsystem.exceptions.AuthenticationFailureException;
import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.helpers.AuthenticationHelper;
import com.telerikacademy.web.forumsystem.mappers.PostMapper;
import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.PostDTO;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostMvcController {

    private final PostService postService;
    private final AuthenticationHelper authenticationHelper;
    private final PostMapper postMapper;

    @Autowired
    public PostMvcController(PostService postService, AuthenticationHelper authenticationHelper, PostMapper postMapper) {
        this.postService = postService;
        this.authenticationHelper = authenticationHelper;
        this.postMapper = postMapper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String listPosts(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "9") int size,
                            HttpSession session) {
        List<Post> paginatedPosts = postService.getPaginatedPosts(page, size);
        long totalPosts = postService.getPostCount();
        int totalPages = (int) Math.ceil((double) totalPosts / size);

        model.addAttribute("posts", paginatedPosts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            user = null;
        }

        model.addAttribute("user", user);

        return "PostsView";
    }

    @GetMapping("/new")
    public String showPostPage(Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        model.addAttribute("post", new PostDTO());
        return "CreatePost";
    }

    @PostMapping("/new")
    public String createNewPost(@Valid @ModelAttribute("post") PostDTO postDTO,
                                BindingResult errors,
                                HttpSession session) {

        if (errors.hasErrors()) {
            return "CreatePost";
        }

        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        Post post = postMapper.fromDto(postDTO);
        postService.create(post, user);
        return "redirect:/posts";
    }

    @GetMapping("/update/{id}")
    public String showPostUpdatePage(@PathVariable int id,
                                     Model model,
                                     HttpSession session) {
        Post post = postService.getById(id);

        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
            if (!user.isAdmin() && post.getCreatedBy().getId() != user.getId()) {
                throw new AuthenticationFailureException("You are not an admin!");
            }
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDenied";
        }
        PostDTO postDTO = postMapper.toDto(post);
        model.addAttribute("post", post);
        return "UpdatePost";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable int id,
                             @Valid @ModelAttribute("post") PostDTO postDto,
                             HttpSession session,
                             BindingResult errors,
                             Model model) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (errors.hasErrors()) {
            return "UpdatePost";
        }

        try {
            Post newPost = postMapper.fromDto(id, postDto);
            postService.update(newPost, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFound";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDenied";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable int id, HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        postService.delete(id, user);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable int id,
                           Model model,
                           HttpSession session) {
        Post post = postService.getById(id);
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
            if (!populateIsAuthenticated(session)) {
                return "redirect:/auth/login";
            }
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "PostNotFound";
//        } catch (UnauthorizedOperationException e) {
//            model.addAttribute("error", e.getMessage());
//            return "UnauthorizedOperation";
//        }
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        return "PostDetailsView";
    }

}
