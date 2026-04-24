package com.workintech.twitter.controller;

import com.workintech.twitter.dto.CommentRequest;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment create(@RequestBody CommentRequest request) {
        return commentService.create(request);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable Long id, @RequestBody CommentRequest request) {
        return commentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam Long userId) {
        commentService.delete(id, userId);
    }
}