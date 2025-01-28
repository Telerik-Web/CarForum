package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.Comment;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface CommentService {

    List<Comment> getByUser(User user);

    List<Comment> getByPost(Post post);

    Comment getById(int id);

    void create(Comment comment,Post post,  User user);

    void update(Comment comment, User user);

    void delete(int id, User user);
}
