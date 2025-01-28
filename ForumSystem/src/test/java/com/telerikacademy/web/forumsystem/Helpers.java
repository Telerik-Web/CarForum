package com.telerikacademy.web.forumsystem;

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
        mockPost.setId(1);
        mockPost.setTitle("Mock Title");
        mockPost.setContent("Mock Content");
        mockPost.setCreatedBy(new User());
        mockPost.setLikes(Set.copyOf(Collections.emptySet()));
        mockPost.setTimestamp(Timestamp.from(Instant.now()));
        return mockPost;
    }
}
