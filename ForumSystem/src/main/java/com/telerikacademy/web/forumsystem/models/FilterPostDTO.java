package com.telerikacademy.web.forumsystem.models;

public class FilterPostDTO {

    private String title;
    private String content;
    private String createdBy;
    private String sortBy;
    private String sortOrder;

    public FilterPostDTO() {
    }

    public static boolean checkIfFilterIsEmpty(FilterPostDTO filterPostDTO){
        return (filterPostDTO.getTitle() == null || filterPostDTO.getTitle().isEmpty()) &&
                (filterPostDTO.getContent() == null || filterPostDTO.getContent().isEmpty()) &&
                (filterPostDTO.getCreatedBy() == null || filterPostDTO.getCreatedBy().isEmpty()) &&
                (filterPostDTO.getSortBy() == null || filterPostDTO.getSortBy().isEmpty()) &&
                (filterPostDTO.getSortOrder() == null || filterPostDTO.getSortOrder().isEmpty());

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
