package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;

import java.util.List;

public interface PostRepository {

    long getPostCount();

    List<Post> getAll(FilterPostOptions filterPostOptions);

    Post getById(int id);

    void createPost(Post post);

    void updatePost(Post post);

    void deletePost(int id);

}
