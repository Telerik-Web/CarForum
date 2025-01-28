package com.telerikacademy.web.forumsystem.mappers;

import com.telerikacademy.web.forumsystem.models.Comment;
import com.telerikacademy.web.forumsystem.models.CommentDTO;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.PostDTO;
import com.telerikacademy.web.forumsystem.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final CommentService commentService;

    @Autowired
    public CommentMapper(CommentService commentService) {
        this.commentService = commentService;
    }

    public Comment fromDTO(int id, CommentDTO dto) {
        Comment comment = commentService.getById(id);

        comment.setContent(dto.getContent());

        return comment;
    }

    public Comment fromDTO(CommentDTO dto) {
        Comment comment = new Comment();

        comment.setContent(dto.getContent());

        return comment;
    }
}
