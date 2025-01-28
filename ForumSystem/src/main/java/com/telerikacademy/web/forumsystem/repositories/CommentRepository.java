package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.models.Comment;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface CommentRepository {

    List<Comment> getByUser(User user);

    List<Comment> getByPost(Post post);

    Comment getById(int id);

    void create(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);
}
