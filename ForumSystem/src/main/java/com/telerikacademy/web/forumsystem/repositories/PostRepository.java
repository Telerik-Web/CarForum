package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;

import java.util.List;

public interface PostRepository {

    long getPostCount();

    List<Post> getAll(FilterPostOptions filterPostOptions);

    public List<Post> getPaginatedPosts(int page, int size);

    Post getById(int id);

    List<Post> getMostRecentPosts();

    List<Post> getMostCommentedPosts();

    void alterPostLikes(Post post);

    void create(Post post);

    void update(Post post);

    void delete(int id);

}
