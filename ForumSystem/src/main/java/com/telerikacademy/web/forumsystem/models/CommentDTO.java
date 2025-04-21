package com.telerikacademy.web.forumsystem.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDTO {

    @NotNull(message = "Content can't be empty")
    @Size(min = 1, max = 2000, message = "Content should be between 1 and 2000 symbols")
    private String content;

    public CommentDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
