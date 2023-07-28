package com.socialblog.controller;

import com.socialblog.payload.CommentDto;
import com.socialblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId,
                                                    @RequestBody CommentDto commentDto){

        CommentDto dto = commentService.createPost(postId, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable("postId") long postId){
        List<CommentDto> dtos = commentService.getCommentByPostId(postId);
        return dtos;
    }

    // http://localhost:8080/api/posts/3/comments/2
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") long postId,
                                              @PathVariable("id") long id){
    commentService.deleteCommentById(postId,id);
        return new ResponseEntity<>("Comment is deleted", HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/3/comments/2
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,
                                                    @PathVariable("id") long id){
        CommentDto dto = commentService.getCommentById(postId, id);
        return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/3/comments/2
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") Long postId,
                                                        @PathVariable("id") Long id,
                                                        @RequestBody CommentDto commentDto){

        CommentDto dto = commentService.updateCommentById(postId, id, commentDto);

        return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
    }
}
