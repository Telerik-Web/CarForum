package com.telerikacademy.web.forumsystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;

    @NotEmpty(message = "Title can't be empty")
    @Size(min = 16, max = 64, message = "Title should be between 16 and 64 symbols")
    private String title;

    @NotEmpty(message = "Content can't be empty")
    @Size(min = 32, max = 8192, message = "Content should be between 31 and 8192 symbols")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @CreationTimestamp
    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();


    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return timestamp.toLocalDateTime().format(formatter);
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public int getLikesSize(){
        return likes.size();
    }

    public void addToLikes(User user){
        likes.add(user);
    }

    public void removeFromLikes(User user){
        likes.remove(user);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public long getCommentsSize(){
        return comments.size();
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
