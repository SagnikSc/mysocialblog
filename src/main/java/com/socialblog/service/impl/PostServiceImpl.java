package com.socialblog.service.impl;

import com.socialblog.entity.Post;
import com.socialblog.exception.ResourceNotFoundException;
import com.socialblog.payload.PostDto;
import com.socialblog.repository.PostRepository;
import com.socialblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post savedPost = postRepo.save(post);

        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepo.findAll(pageable);
        List<Post> content = posts.getContent();

        return content.stream().map(post->mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        Post updatedContent = mapToEntity(postDto);
        updatedContent.setId(post.getId());
        Post savedPost = postRepo.save(updatedContent);
        return mapToDto(savedPost);
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    PostDto mapToDto(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }
}
