package com.swagger.test.dto;

import com.swagger.test.domain.Comment;

public class CommentRequest {
    private Long id;
    private Long post_id;
    private String comment;

    protected CommentRequest() {}

    public Comment toEntity() {
        return new Comment(comment);
    }

    public String getComment() {
        return comment;
    }
}
