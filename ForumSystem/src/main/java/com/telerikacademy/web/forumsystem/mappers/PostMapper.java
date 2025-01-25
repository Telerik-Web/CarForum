package com.telerikacademy.web.forumsystem.mappers;

import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.PostDTO;
import com.telerikacademy.web.forumsystem.models.PostDTOOut;
import com.telerikacademy.web.forumsystem.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public PostDTOOut toDTOOut(Post post) {
        PostDTOOut dto = new PostDTOOut();
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUsername(post.getCreatedBy().getUsername());
        return dto;
    }

    public List<PostDTOOut> toDTOOut(List<Post> postList){
        List<PostDTOOut> postDTOS = new ArrayList<>();
        for (Post post : postList){
            PostDTOOut postDTOOut = new PostDTOOut();
            postDTOOut.setTitle(post.getTitle());
            postDTOOut.setContent(post.getContent());
            postDTOOut.setUsername(post.getCreatedBy().getUsername());
            postDTOS.add(postDTOOut);
        }

        return postDTOS;
    }
}
