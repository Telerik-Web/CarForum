package com.telerikacademy.web.forumsystem;

import com.telerikacademy.web.forumsystem.models.Comment;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;

public class Helpers {

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setFirstName("John");
        mockUser.setLastName("mockLastName");
        mockUser.setUsername("MockUsername");
        mockUser.setEmail("mock@user.com");
        mockUser.setPassword("password");
        mockUser.setAdmin(false);
        mockUser.setBlocked(false);
        //mockUser.setRoles(Set.of(createMockRole()));
        return mockUser;
    }

    public static Post createMockPost() {
        var mockPost = new Post();
        User user = createMockUser();
        user.setId(2);
        mockPost.setId(1);
        mockPost.setTitle("Mock Title");
        mockPost.setContent("Mock Content");
        mockPost.setCreatedBy(user);
        mockPost.setLikes(Set.copyOf(Collections.emptySet()));
        mockPost.setTimestamp(Timestamp.from(Instant.now()));
        return mockPost;
    }

    public static Comment createMockComment() {
        var mockComment = new Comment();
        User user = createMockUser();
        user.setId(2);
        mockComment.setId(1);
        mockComment.setContent("Mock Content");
        mockComment.setCreatedBy(user);
        mockComment.setPost(createMockPost());
        return mockComment;
    }
}
