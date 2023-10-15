package com.blog.blogdemo.controller;

import com.blog.blogdemo.payload.CommentDto;
import com.blog.blogdemo.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")

public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> saveComment(@PathVariable  long postId, @RequestBody CommentDto commentDto){
        CommentDto commentdto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(commentdto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        List<CommentDto> commentsByPostId = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentsByPostId,HttpStatus.OK);
    }

//http://localhost:8080/api/posts/{postId}/comments/{id}
    @GetMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentsByCommentId(@PathVariable(value = "postId") long postId,
                                                             @PathVariable(value = "id") long id){
    CommentDto comments = commentService.getCommentsByCommentId(postId, id);
    return new ResponseEntity<>(comments,HttpStatus.OK);
}

@PutMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "id") long id,
                                                    @RequestBody CommentDto commentDto){

    CommentDto updatedComment = commentService.updateComments(postId, id, commentDto);
    return new ResponseEntity<>(updatedComment,HttpStatus.OK);

}

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "id") long id){
        commentService.deleteComments(postId,id);
        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }

}
