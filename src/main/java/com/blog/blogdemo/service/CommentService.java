package com.blog.blogdemo.service;

import com.blog.blogdemo.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentsByCommentId(long postId, long id);

    CommentDto updateComments(long postId,long id,CommentDto commentDto);

    void deleteComments(long postId, long id);
}
