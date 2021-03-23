package com.swagger.test.dto;

import com.swagger.test.domain.Post;

public class PostRequest {
    private String title;
    private String content;

    protected PostRequest() {}

    public Post toEntity() {
        return new Post(this.title, this.content);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
