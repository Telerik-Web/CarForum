package com.telerikacademy.web.forumsystem.models;


public class PostDTOOut {

    private String title;

    private String content;

    private String username;

    public PostDTOOut() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
