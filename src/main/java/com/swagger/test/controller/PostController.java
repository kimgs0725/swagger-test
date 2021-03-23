package com.swagger.test.controller;

import com.swagger.test.domain.Comment;
import com.swagger.test.domain.Post;
import com.swagger.test.dto.PostRequest;
import com.swagger.test.dto.PostResponse;
import com.swagger.test.repository.CommentRepository;
import com.swagger.test.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public PostController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
            PostResponse response = PostResponse.of(post);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll() {
        List<Post> postList = postRepository.findAll();
        List<PostResponse> collect = postList.stream().map(PostResponse::of).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @PostMapping
    public ResponseEntity<PostResponse> save(@RequestBody final PostRequest request) {
        PostResponse response = PostResponse.of(postRepository.save(request.toEntity()));
        return ResponseEntity.created(URI.create("/posts/" + response.getId())).body(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        List<Comment> commentList = post.getCommentList();
        for (Comment comment : commentList) {
            commentRepository.delete(comment);
        }
        commentList.clear();
        postRepository.delete(post);
        return ResponseEntity.noContent().build();
    }
}
