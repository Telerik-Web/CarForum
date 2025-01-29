package com.telerikacademy.web.forumsystem.services;


import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.models.Comment;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.web.forumsystem.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void getByUser_ShouldReturn_When_Valid() {
        User user = createMockUser();
        List<Comment> comments = new ArrayList<>();
        Comment comment = createMockComment();
        comments.add(comment);

        Mockito.when(commentService.getByUser(user))
                .thenReturn(comments);

        Assertions.assertEquals(comments, commentService.getByUser(user));
    }

    @Test
    public void getByPost_ShouldReturn_When_Valid() {
        Post post = createMockPost();
        List<Comment> comments = new ArrayList<>();
        Comment comment = createMockComment();
        comments.add(comment);

        Mockito.when(commentService.getByPost(post))
                .thenReturn(comments);

        Assertions.assertEquals(comments, commentService.getByPost(post));
    }

    @Test
    public void getById_ShouldReturn_When_Valid() {
        Comment comment = createMockComment();
        Mockito.when(commentService.getById(comment.getId()))
                .thenReturn(comment);
        Assertions.assertEquals(comment, commentService.getById(comment.getId()));
    }

    @Test
    public void create_Should_Throw_When_UserIsBlocked() {
        Comment comment = createMockComment();
        Post post = createMockPost();
        User user = createMockUser();
        user.setBlocked(true);
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.create(comment, post, user)
        );
    }

    @Test
    public void create_Should_Create_When_Valid() {
        Comment comment = createMockComment();
        Post post = createMockPost();
        User user = createMockUser();
        Assertions.assertDoesNotThrow(() ->
                commentService.create(comment, post, user)
        );
    }

    @Test
    public void update_Should_Throw_When_UserIsBlocked() {
        Comment comment = createMockComment();
        User user = createMockUser();
        user.setBlocked(true);
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.update(comment, user)
        );
    }

    @Test
    public void update_Should_Throw_When_UserIsNotAnAdmin() {
        Comment comment = createMockComment();
        User user = createMockUser();
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.update(comment, user)
        );
    }

    @Test
    public void update_Should_Create_When_Valid() {
        Comment comment = createMockComment();
        User user = createMockUser();
        user.setAdmin(true);
        Assertions.assertDoesNotThrow(() ->
                commentService.update(comment, user)
        );
    }

    @Test
    public void delete_Should_Throw_When_UserIsBlocked() {
        Comment comment = createMockComment();
        User user = createMockUser();
        user.setBlocked(true);
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.delete(comment.getId(), user)
        );
    }

    @Test
    public void delete_Should_Throw_When_UserIsNotAnAdmin() {
        Comment comment = createMockComment();

        Mockito.when(commentService.getById(comment.getId()))
                .thenReturn(comment);

        User user = createMockUser();

        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.delete(comment.getId(), user)
        );
    }

    @Test
    public void delete_Should_Create_When_Valid() {

        Comment comment = createMockComment();
        Mockito.when(commentService.getById(comment.getId()))
                .thenReturn(comment);

        User user = createMockUser();
        comment.setCreatedBy(user);
        user.setAdmin(true);

        Assertions.assertDoesNotThrow(() ->
                commentService.delete(comment.getId(), user)
        );
    }

}
