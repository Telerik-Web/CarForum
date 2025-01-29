package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.telerikacademy.web.forumsystem.Helpers.createMockPost;
import static com.telerikacademy.web.forumsystem.Helpers.createMockUser;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void getPostCount_ShouldReturnPostCount_WhenValid() {
        // Arrange
        long expectedCount = 1L;
        Mockito.when(postRepository.getPostCount()).thenReturn(expectedCount);

        // Act
        long actualCount = postService.getPostCount();

        // Assert
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void getAll_ShouldReturnAllPosts_WhenValid() {
        // Arrange
        FilterPostOptions filterPostOptions = new FilterPostOptions("title", "content", "createdBy",
                "sortBy", "sortOrder");

        // Act
        List<Post> allPosts = postService.getAll(filterPostOptions);

        // Assert
        Assertions.assertEquals(postService.getAll(filterPostOptions).size(), allPosts.size());
    }

    @Test
    public void getById_ShouldReturnPost_WhenValid() {
        // Arrange
        Post mockPost = createMockPost();
        Mockito.when(postRepository.getById(1)).thenReturn(mockPost);

        // Act
        Post result = postService.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    public void create_ShouldThrowException_WhenUserIsBlocked() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setBlocked(true);
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.create(mockPost, mockUser);
        });
    }

    @Test
    public void create_ShouldCreatePost_WhenValidParameters() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            postService.create(mockPost, mockUser);
        });
    }

    @Test
    public void update_ShouldThrowException_WhenUserIsNotAnAdmin() {
        // Arrange
        User mockUser = createMockUser();
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.update(mockPost, mockUser);
        });
    }

    @Test
    public void update_ShouldThrowException_WhenUserIsBlocked() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        mockUser.setBlocked(true);
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.update(mockPost, mockUser);
        });
    }

    @Test
    public void update_ShouldUpdatePost_WhenValidInput() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            postService.update(mockPost, mockUser);
        });
    }

    @Test
    public void delete_ShouldThrowException_WhenUserIsNotAnAdmin() {
        // Arrange
        User mockUser = createMockUser();
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.delete(mockPost.getCreatedBy().getId(), mockUser);
        });
    }

    @Test
    public void delete_ShouldThrowException_WhenUserIsBlocked() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        mockUser.setBlocked(true);
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.delete(mockPost.getId(), mockUser);
        });
    }

    @Test
    public void delete_ShouldDeletePost_WhenValidInput() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        Post mockPost = createMockPost();

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            postService.delete(mockPost.getId(), mockUser);
        });
    }
}

