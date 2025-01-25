package com.telerikacademy.web.forumsystem.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostDTOOut {
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 16, max = 64, message = "Title should be between 16 and 64 symbols")
    private String title;
    @NotEmpty(message = "Content can't be empty")
    @Size(min = 32, max = 8192, message = "Content should be between 31 and 8192 symbols")
    private String content;
    @NotNull(message = "Username can't be empty.")
    @Size(min = 4, max = 32, message = "Username should be between 4 and 32 symbols.")
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
