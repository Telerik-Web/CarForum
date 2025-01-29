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
    public void getByUser_ShouldReturnComments_WhenValidUser() {
        // Arrange
        User user = createMockUser();
        List<Comment> comments = new ArrayList<>();
        Comment comment = createMockComment();
        comments.add(comment);

        Mockito.when(commentService.getByUser(user))
                .thenReturn(comments);

        // Act
        List<Comment> result = commentService.getByUser(user);

        // Assert
        Assertions.assertEquals(comments, result);
    }

    @Test
    public void getByPost_ShouldReturnComments_WhenValidPost() {
        // Arrange
        Post post = createMockPost();
        List<Comment> comments = new ArrayList<>();
        Comment comment = createMockComment();
        comments.add(comment);

        Mockito.when(commentService.getByPost(post))
                .thenReturn(comments);

        // Act
        List<Comment> result = commentService.getByPost(post);

        // Assert
        Assertions.assertEquals(comments, result);
    }

    @Test
    public void getById_ShouldReturnComment_WhenValidId() {
        // Arrange
        Comment comment = createMockComment();
        Mockito.when(commentService.getById(comment.getId()))
                .thenReturn(comment);

        // Act
        Comment result = commentService.getById(comment.getId());

        // Assert
        Assertions.assertEquals(comment, result);
    }

    @Test
    public void create_ShouldThrowException_WhenUserIsBlocked() {
        // Arrange
        Comment comment = createMockComment();
        Post post = createMockPost();
        User user = createMockUser();
        user.setBlocked(true);

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.create(comment, post, user)
        );
    }

    @Test
    public void create_ShouldCreateComment_WhenValid() {
        // Arrange
        Comment comment = createMockComment();
        Post post = createMockPost();
        User user = createMockUser();

        // Act & Assert
        Assertions.assertDoesNotThrow(() ->
                commentService.create(comment, post, user)
        );
    }

    @Test
    public void update_ShouldThrowException_WhenUserIsBlocked() {
        // Arrange
        Comment comment = createMockComment();
        User user = createMockUser();
        user.setBlocked(true);

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.update(comment, user)
        );
    }

    @Test
    public void update_ShouldThrowException_WhenUserIsNotAdmin() {
        // Arrange
        Comment comment = createMockComment();
        User user = createMockUser();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.update(comment, user)
        );
    }

    @Test
    public void update_ShouldUpdateComment_WhenValidUserAndAdmin() {
        // Arrange
        Comment comment = createMockComment();
        User user = createMockUser();
        user.setAdmin(true);

        // Act & Assert
        Assertions.assertDoesNotThrow(() ->
                commentService.update(comment, user)
        );
    }

    @Test
    public void delete_ShouldThrowException_WhenUserIsBlocked() {
        // Arrange
        Comment comment = createMockComment();
        User user = createMockUser();
        user.setBlocked(true);

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.delete(comment.getId(), user)
        );
    }

    @Test
    public void delete_ShouldThrowException_WhenUserIsNotAdmin() {
        // Arrange
        Comment comment = createMockComment();
        Mockito.when(commentService.getById(comment.getId()))
                .thenReturn(comment);
        User user = createMockUser();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                commentService.delete(comment.getId(), user)
        );
    }

    @Test
    public void delete_ShouldDeleteComment_WhenValidUserAndAdmin() {
        // Arrange
        Comment comment = createMockComment();
        Mockito.when(commentService.getById(comment.getId()))
                .thenReturn(comment);
        User user = createMockUser();
        comment.setCreatedBy(user);
        user.setAdmin(true);

        // Act & Assert
        Assertions.assertDoesNotThrow(() ->
                commentService.delete(comment.getId(), user)
        );
    }
}

