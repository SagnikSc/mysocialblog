package com.socialblog.service;

import com.socialblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createPost(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);

    void deleteCommentById(long postId, long id);

    CommentDto getCommentById(long postId, long id);


    CommentDto updateCommentById(Long postId, long id, CommentDto commentDto);
}
