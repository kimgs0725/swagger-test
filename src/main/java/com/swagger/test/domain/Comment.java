package com.swagger.test.domain;

import javax.persistence.*;

@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "post_id",
            nullable = false
    )
    private Post post;

    @Column(nullable = false)
    private String comment;

    protected Comment() {}

    public Comment(String comment) {
        this.comment = comment;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getComment() {
        return comment;
    }
}
