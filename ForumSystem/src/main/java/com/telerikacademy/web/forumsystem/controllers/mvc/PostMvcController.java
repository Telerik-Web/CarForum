package com.telerikacademy.web.forumsystem.controllers.mvc;

import com.telerikacademy.web.forumsystem.exceptions.AuthenticationFailureException;
import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.helpers.AuthenticationHelper;
import com.telerikacademy.web.forumsystem.mappers.CommentMapper;
import com.telerikacademy.web.forumsystem.mappers.PostMapper;
import com.telerikacademy.web.forumsystem.models.*;
import com.telerikacademy.web.forumsystem.services.CommentService;
import com.telerikacademy.web.forumsystem.services.CommentServiceImpl;
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
import java.util.Set;

@Controller
@RequestMapping("/posts")
public class PostMvcController {

    private final PostService postService;
    private final AuthenticationHelper authenticationHelper;
    private final PostMapper postMapper;
    private final CommentService commentService;
    private final CommentServiceImpl commentServiceImpl;
    private final CommentMapper commentMapper;

    @Autowired
    public PostMvcController(PostService postService, AuthenticationHelper authenticationHelper, PostMapper postMapper, CommentService commentService, CommentServiceImpl commentServiceImpl, CommentMapper commentMapper) {
        this.postService = postService;
        this.authenticationHelper = authenticationHelper;
        this.postMapper = postMapper;
        this.commentService = commentService;
        this.commentServiceImpl = commentServiceImpl;
        this.commentMapper = commentMapper;
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
            return "AccessDenied";
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
            return "AccessDenied";
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
            return "AccessDenied";
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
                             @Valid @ModelAttribute("post") Post post,
                             BindingResult errors,
                             HttpSession session,
                             Model model) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "AccessDenied";
        }

        if (errors.hasErrors()) {
            return "UpdatePost";
        }

        try {
            postService.update(post, user);
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
    public String deletePost(@PathVariable int id,
                             HttpSession session) {
        User user = authenticationHelper.tryGetUser(session);
        postService.delete(id, user);
        return "redirect:/posts";

    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable int id,
                           Model model,
                           HttpSession session) {
        Post post = postService.getById(id);
        Set<Comment> comments = post.getComments();
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
            if (!populateIsAuthenticated(session)) {
                return "AccessDenied";
            }
        } catch (AuthenticationFailureException e) {
            return "AccessDenied";
        }

        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        return "PostDetailsView";
    }

    @GetMapping("/{id}/comment")
    public String addComment(@PathVariable int id,
                             Model model,
                             HttpSession session) {
        Post post = postService.getById(id);
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "AccessDenied";
        }
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        model.addAttribute("comment", new CommentDTO());
        return "Comment";
    }

    @PostMapping("/{id}/comment")
    public String addCommentForm(@Valid @ModelAttribute("comment") CommentDTO commentDTO,
                                 @ModelAttribute("post") Post post,
                                 HttpSession session,
                                 BindingResult errors,
                                 Model model) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "AccessDenied";
        }

        if (errors.hasErrors()) {
            return "Comment";
        }

        try {
            Comment newComment = commentMapper.fromDTO(commentDTO);
            commentService.create(newComment, post, user);
            return "redirect:/posts/{id}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFound";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDenied";
        }
    }

    @GetMapping("/{id}/comment/delete/{commentId}")
    public String deleteComment(@PathVariable int id,
                                @PathVariable int commentId,
                                HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "AccessDenied";
        }
        commentService.delete(commentId, user);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{postId}/comment/update/{commentId}")
    public String updateComment(@PathVariable int postId,
                                @PathVariable int commentId,
                                HttpSession session,
                                Model model) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            Comment comment = commentService.getById(commentId);
            Post post = postService.getById(postId);

            model.addAttribute("user", user);
            model.addAttribute("post", post);
            model.addAttribute("comment", comment);

            return "CommentUpdate";
        } catch (AuthenticationFailureException e) {
            return "AccessDenied";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFound";
        }
    }

    @PostMapping("/{postId}/comment/update/{commentId}")
    public String updateCommentForm(@PathVariable int postId,
                                    @PathVariable int commentId,
                                    @Valid @ModelAttribute("comment") Comment comment,
                                    @ModelAttribute("user") User user,
                                    @ModelAttribute("post") Post post,
                                    BindingResult errors,
                                    HttpSession session,
                                    Model model) {
        if (errors.hasErrors()) {
            return "CommentUpdate";
        }

        try {
            User loggedInUser = authenticationHelper.tryGetUser(session);
            Comment existingComment = commentService.getById(commentId);

            existingComment.setContent(comment.getContent());

            commentService.update(existingComment, loggedInUser);

            return "redirect:/posts/" + postId; // Redirect to post page
        } catch (EntityNotFoundException | UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFound";
        }
    }

//    @GetMapping("/{id}/comment/update/{commentId}")
//    public String updateComment(@PathVariable int id,
//                                @PathVariable int commentId,
//                                HttpSession session,
//                                Model model) {
//        User user;
//        try {
//            user = authenticationHelper.tryGetUser(session);
//        } catch (AuthenticationFailureException e) {
//            return "AccessDenied";
//        }
//        model.addAttribute("user", user);
//        model.addAttribute("post", postService.getById(id));
//        model.addAttribute("comment", commentService.getById(commentId));
//        return "CommentUpdate";
//    }

//    @PostMapping("/{id}/comment/update/{commentId}")
//    public String updateCommentForm(@PathVariable int postId,
//                                    @PathVariable int commentId,
//                                    @Valid @ModelAttribute("comment") Comment comment,
//                                    @ModelAttribute("user") User user,
//                                    @ModelAttribute("post") Post post,
//                                    BindingResult errors,
//                                    HttpSession session,
//                                    Model model) {
//
//        if (errors.hasErrors()) {
//            return "CommentUpdate";
//        }
//
//        try {
//            User loggedin = authenticationHelper.tryGetUser(session);
//            Comment existingComment = commentService.getById(commentId);
//            existingComment.setContent(comment.getContent());
//            commentService.update(comment, user);
//            return "redirect:/posts/" + post.getId();
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "NotFound";
//        } catch (UnauthorizedOperationException e) {
//            model.addAttribute("error", e.getMessage());
//            return "AccessDenied";
//        }
//    }
}
