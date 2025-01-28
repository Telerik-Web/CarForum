package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.Comment;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.telerikacademy.web.forumsystem.helpers.PermissionHelper.checkIfBlocked;
import static com.telerikacademy.web.forumsystem.helpers.PermissionHelper.checkIfCreatorOrAdmin;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getByUser(User user) {
        return commentRepository.getByUser(user);
    }

    @Override
    public List<Comment> getByPost(Post post) {
        return commentRepository.getByPost(post);
    }

    @Override
    public Comment getById(int id) {
        return commentRepository.getById(id);
    }

    @Override
    public void create(Comment comment,Post post, User user) {
        checkIfBlocked(user);

        comment.setPost(post);
        comment.setCreatedBy(user);

        commentRepository.create(comment);

    }

    @Override
    public void update(Comment comment, User user) {
        checkIfBlocked(user);
        checkIfCreatorOrAdmin(comment.getCreatedBy().getId(), user);

        commentRepository.update(comment);
    }

    @Override
    public void delete(int id, User user) {
        Comment comment = commentRepository.getById(id);

        checkIfBlocked(user);
        checkIfCreatorOrAdmin(comment.getCreatedBy().getId(), user);

        commentRepository.delete(comment);
    }
}
