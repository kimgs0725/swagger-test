package com.swagger.test.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false)
    private String content;

    protected Post() {}

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment) {
        if (!this.commentList.contains(comment)) {
            this.commentList.add(comment);
            comment.setPost(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
