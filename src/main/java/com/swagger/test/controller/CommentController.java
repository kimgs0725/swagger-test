package com.swagger.test.controller;

import com.swagger.test.domain.Comment;
import com.swagger.test.domain.Post;
import com.swagger.test.dto.CommentRequest;
import com.swagger.test.dto.CommentResponse;
import com.swagger.test.repository.CommentRepository;
import com.swagger.test.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post/{postId}/comment")
public class CommentController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll(@PathVariable Long postId) {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponse> collect = commentList.stream().map(CommentResponse::of).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        try {
            Comment comment = commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            CommentResponse response = CommentResponse.of(comment);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<CommentResponse> save(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
            Comment comment = commentRequest.toEntity();
            post.addComment(comment);
            CommentResponse response = CommentResponse.of(commentRepository.save(comment));
            return ResponseEntity.created(URI.create("/posts/" + postId + "/comment/" + comment.getId())).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        commentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }
}
