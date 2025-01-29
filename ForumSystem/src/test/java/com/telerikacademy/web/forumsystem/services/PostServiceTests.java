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
    public void getPostCount_Should_ReturnPostCount_When_Valid() {
        long expectedCount = 1L;
        Mockito.when(postRepository.getPostCount()).thenReturn(expectedCount);

        long actualCount = postService.getPostCount();

        Assertions.assertEquals(expectedCount, actualCount);
    }

    //CHECK
    @Test
    public void getAll_Should_ReturnAll_When_Valid() {
        FilterPostOptions filterPostOptions = new FilterPostOptions("title", "content", "createdBy",
                "sortBy", "sortOrder");
        List<Post> getAll = postService.getAll(filterPostOptions);
        Assertions.assertEquals(postService.getAll(filterPostOptions).size(), getAll.size());
    }

    @Test
    public void getById_Should_ReturnPost_When_Valid() {
        Post mockPost = createMockPost();
        Mockito.when(postRepository.getById(1))
                .thenReturn(mockPost);

        Post result = postService.getById(1);
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    public void create_Should_Throw_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setBlocked(true);
        Post mockPost = createMockPost();
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.create(mockPost, mockUser);
        });
    }

    @Test
    public void create_Should_Create_When_ValidParameters() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        Post mockPost = createMockPost();
        Assertions.assertDoesNotThrow(() -> {
            postService.create(mockPost, mockUser);
        });
    }

    @Test
    public void Update_Should_Throw_When_UserIsNotAnAdmin() {
        User mockUser = createMockUser();
        Post mockPost = createMockPost();
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.update(mockPost, mockUser);
        });
    }

    @Test
    public void Update_Should_Throw_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        mockUser.setBlocked(true);
        Post mockPost = createMockPost();

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.update(mockPost, mockUser);
        });
    }

    @Test
    public void Update_Should_Update_When_ValidInput() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        Post mockPost = createMockPost();
        Assertions.assertDoesNotThrow(() ->
                postService.update(mockPost, mockUser)
        );
    }

    @Test
    public void delete_Should_Throw_When_UserIsNotAnAdmin() {
        User mockUser = createMockUser();
        Post mockPost = createMockPost();

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.delete(mockPost.getCreatedBy().getId(), mockUser);
        });
    }

    @Test
    public void delete_Should_Throw_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        mockUser.setBlocked(true);
        Post mockPost = createMockPost();
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            postService.delete(mockPost.getId(), mockUser);
        });
    }

    @Test
    public void delete_Should_Delete_When_ValidInput() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        Post mockPost = createMockPost();

        Assertions.assertDoesNotThrow(() -> {
            postService.delete(mockPost.getId(), mockUser);
        });
    }

}
