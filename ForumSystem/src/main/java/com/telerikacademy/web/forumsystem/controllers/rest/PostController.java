package com.telerikacademy.web.forumsystem.controllers.rest;

import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.helpers.AuthenticationHelper;
import com.telerikacademy.web.forumsystem.mappers.PostMapper;
import com.telerikacademy.web.forumsystem.models.*;
import com.telerikacademy.web.forumsystem.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post Controller", description = "APIs for managing posts")
public class PostController {

    private final PostService postService;
    private final AuthenticationHelper authenticationHelper;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, AuthenticationHelper authenticationHelper, PostMapper postMapper) {
        this.postService = postService;
        this.authenticationHelper = authenticationHelper;
        this.postMapper = postMapper;
    }

    @Operation(summary = "Returns all posts", description = "Returns all posts with their information")
    @GetMapping
    public List<PostDTOOut> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        FilterPostOptions filterOptions = new FilterPostOptions(title, content, createdBy,
                sortBy, sortOrder);

        return postMapper.toDTOOut(postService.getAll(filterOptions));
    }

    @Operation(summary = "Get post by ID", description = "Fetches a post by their unique ID")
    @GetMapping("/{postId}")
    public Post getById(@PathVariable int postId) {
        try {
            return postService.getById(postId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Returns the most recent post", description = "Returns the most recent post by timestamp")
    @GetMapping("/mostRecent")
    public List<Post> getMostRecentPosts() {
        return postService.getMostRecentPosts();
    }

    @Operation(summary = "Returns the most commented post", description = "Returns the most commented post by analyzing " +
            "the number of comments associated with each post.")
    @GetMapping("/mostCommented")
    public List<Post> getMostCommentedPosts() {
        return postService.getMostCommentedPosts();
    }

    @Operation(summary = "Creates a post", description = "Create a post in the DB with all its required data.")
    @PostMapping
    @SecurityRequirement(name = "authHeader")
    public Post create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PostDTO postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromDto(postDto);
            postService.create(post, user);
            return post;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Updates a post by ID", description = "updates the defined fields of a post with the new values.")
    @PutMapping("/{postId}")
    @SecurityRequirement(name = "authHeader")
    public Post update(@RequestHeader HttpHeaders headers,
                       @PathVariable int postId,
                       @Valid @RequestBody PostDTO postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromDto(postId, postDto);
            postService.update(post, user);
            return post;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Like or Dislike a post", description =
                        "If isLiked is true, likes a post, else dislikes a post")
    @PatchMapping("/{postId}/like")
    @SecurityRequirement(name = "authHeader")
    public void alterPostLikes(@PathVariable int postId, @RequestHeader HttpHeaders headers, @RequestParam boolean isLiked) {
        try{
            User user = authenticationHelper.tryGetUser(headers);
            postService.alterPostLikes(postId, user, isLiked);
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (UnauthorizedOperationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Delete post by ID", description = "Deletes a post by it's unique ID")
    @DeleteMapping("/{postId}")
    @SecurityRequirement(name = "authHeader")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int postId) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            postService.delete(postId, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
