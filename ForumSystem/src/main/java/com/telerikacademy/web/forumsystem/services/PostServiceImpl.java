package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.FilterPostOptions;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.telerikacademy.web.forumsystem.helpers.PermissionHelper.checkIfBlocked;
import static com.telerikacademy.web.forumsystem.helpers.PermissionHelper.checkIfCreatorOrAdmin;

@Service
public class PostServiceImpl implements PostService{
    PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public long getPostCount() {
        return postRepository.getPostCount();
    }

    @Override
    public List<Post> getAll(FilterPostOptions filterPostOptions) {
        return postRepository.getAll(filterPostOptions);
    }

    @Override
    public Post getById(int id) {
        return postRepository.getById(id);
    }

    @Override
    public void createPost(Post post, User user) {
        checkIfBlocked(user);

        post.setCreatedBy(user);
        postRepository.createPost(post);
    }

    @Override
    public void updatePost(Post post, User user) {
        checkIfCreatorOrAdmin(user, post);
        checkIfBlocked(user);

        postRepository.updatePost(post);
    }

    @Override
    public void deletePost(int id, User user) {
        checkIfCreatorOrAdmin(id, user);
        checkIfBlocked(user);

        postRepository.deletePost(id);
    }
}
