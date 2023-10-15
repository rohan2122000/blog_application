package com.blog.blogdemo.service;

import com.blog.blogdemo.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
