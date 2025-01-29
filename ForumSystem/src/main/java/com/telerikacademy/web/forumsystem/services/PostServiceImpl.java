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
    public List<Post> getMostRecentPosts() {
        return postRepository.getMostRecentPosts();
    }

    @Override
    public List<Post> getMostCommentedPosts() {
        return postRepository.getMostCommentedPosts();
    }

    @Override
    public void create(Post post, User user) {
        checkIfBlocked(user);

        post.setCreatedBy(user);
        postRepository.create(post);
    }

    @Override
    public void update(Post post, User user) {
        checkIfCreatorOrAdmin(user, post);
        checkIfBlocked(user);

        postRepository.update(post);
    }

    @Override
    public void delete(int id, User user) {
        checkIfCreatorOrAdmin(id, user);
        checkIfBlocked(user);

        postRepository.delete(id);
    }

    @Override
    public void alterPostLikes(int id, User user, boolean isLiked) {
        checkIfBlocked(user);

        Post postToUpdate = postRepository.getById(id);

        if (isLiked) {
            postToUpdate.addToLikes(user);
        }
        if (!isLiked) {
            postToUpdate.removeFromLikes(user);
        }
        postRepository.alterPostLikes(postToUpdate);
    }
}
