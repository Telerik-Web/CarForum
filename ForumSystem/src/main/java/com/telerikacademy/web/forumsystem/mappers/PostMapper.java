package com.telerikacademy.web.forumsystem.mappers;

import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.PostDTO;
import com.telerikacademy.web.forumsystem.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final PostService postService;

    @Autowired
    public PostMapper(PostService postService) {
        this.postService = postService;
    }

    public Post fromDto(int id, PostDTO dto) {
        Post post = postService.getById(id);

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        return post;
    }

    public Post fromDto(PostDTO dto) {
        Post post = new Post();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        return post;
    }


//    Can be used to print the new objects info
//    public PostDTO toDTOOut(Post post) {
//        PostDTO dto = new PostDTO();
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        return dto;
//    }
}
