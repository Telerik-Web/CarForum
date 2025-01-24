package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface PostService {

    long getPostCount();

    List<Post> getAll(FilterPostOptions filterPostOptions);

    Post getById(int id);

    void createPost(Post post, User user);

    void updatePost(Post post, User user);

    void deletePost(int id, User user);
}
