package com.swagger.test.dto;

import com.swagger.test.domain.Comment;

public class CommentResponse {
    private Long id;
    private Long post_id;
    private String comment;

    private CommentResponse(Long id, Long post_id, String comment) {
        this.id = id;
        this.post_id = post_id;
        this.comment = comment;
    }

    public static CommentResponse of(final Comment comment) {
        return new CommentResponse(comment.getId(), comment.getPost().getId(), comment.getComment());
    }

    public Long getId() {
        return id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public String getComment() {
        return comment;
    }
}
