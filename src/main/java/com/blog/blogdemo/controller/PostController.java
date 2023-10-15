package com.blog.blogdemo.controller;

import com.blog.blogdemo.payload.PostDto;
import com.blog.blogdemo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    

    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam(value="pageNo",defaultValue = "0",required = false)int pageNo,
                                     @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                     @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
}
