package com.blog.blogdemo.service.impl;

import com.blog.blogdemo.entity.Comment;
import com.blog.blogdemo.entity.Post;
import com.blog.blogdemo.exception.BlogAPIException;
import com.blog.blogdemo.exception.EntityNotFoundException;
import com.blog.blogdemo.payload.CommentDto;
import com.blog.blogdemo.payload.PostDto;
import com.blog.blogdemo.repository.CommentRepository;
import com.blog.blogdemo.repository.PostRepository;
import com.blog.blogdemo.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;



    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException("No record found"));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment save = commentRepository.save(comment);
        CommentDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentsByCommentId(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("post not found"));

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment not found"));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belongs to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComments(long postId, long id, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("post not found"));

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment not found"));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belongs to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void  deleteComments(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("post not found"));

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment not found"));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belongs to post");
        }
        commentRepository.delete(comment);

    }

    Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto,Comment.class);
        return comment;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
        return commentDto;
    }
}
