package com.socialblog.service.impl;

import com.socialblog.entity.Comment;
import com.socialblog.entity.Post;
import com.socialblog.exception.ResourceNotFoundException;
import com.socialblog.payload.CommentDto;
import com.socialblog.repository.CommentRepository;
import com.socialblog.repository.PostRepository;
import com.socialblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createPost(long postId, CommentDto commentDto) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment savedComment = commentRepo.save(comment);
        CommentDto dto = mapToDto(savedComment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));

        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));

        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));

        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        CommentDto commentDto = mapToDto(comment);

        return commentDto;
    }

    @Override
    public CommentDto updateCommentById(Long postId, long id, CommentDto commentDto) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));

        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        Comment updatedContent = mapToEntity(commentDto);
        updatedContent.setId(comment.getId());
        updatedContent.setPost(post);

        Comment savedComment = commentRepo.save(updatedContent);

        return mapToDto(savedComment);
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }
}
