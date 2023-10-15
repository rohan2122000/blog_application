package com.blog.blogdemo.service.impl;

import com.blog.blogdemo.entity.Post;
import com.blog.blogdemo.payload.PostDto;
import com.blog.blogdemo.repository.PostRepository;
import com.blog.blogdemo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post save = postRepository.save(post);
        PostDto postDto1 = mapToDto(save);
        return postDto1;

    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> content = postRepository.findAll(pageable);
        List<Post> posts = content.getContent();
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto,Post.class);
        return post;
    }

    PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post,PostDto.class);
        return postDto;
    }
}
