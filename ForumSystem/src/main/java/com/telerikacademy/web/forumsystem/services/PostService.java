package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface PostService {

    long getPostCount();

    List<Post> getAll(FilterPostOptions filterPostOptions);

    Post getById(int id);

    List<Post> getMostRecentPosts();

    List<Post> getMostCommentedPosts();

    void alterPostLikes(int id, User user, boolean isLiked);

    void create(Post post, User user);

    void update(Post post, User user);

    void delete(int id, User user);
}
