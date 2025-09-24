package com.unnkk.elsechat.controllers;

import com.unnkk.elsechat.DTOs.PostRequestDTO;
import com.unnkk.elsechat.DTOs.PostResponseDTO;
import com.unnkk.elsechat.entities.Post;
import com.unnkk.elsechat.exceptions.BadRequestException;
import com.unnkk.elsechat.exceptions.NotFoundException;
import com.unnkk.elsechat.exceptions.UnprocessableEntityException;
import com.unnkk.elsechat.repositories.PostRepository;
import com.unnkk.elsechat.services.PostService;
import com.unnkk.elsechat.utils.PostMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id){
        if(!postRepository.existsById(id)){
            throw new NotFoundException("Cannot find post with id " + id);
        }
        return ResponseEntity.ok(PostMapper.toResponseDTO(postService.getPostById(id)));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createPost(@RequestBody Map<String, String> body){
        if(!body.containsKey("content")){
            throw new BadRequestException("Missing content");
        }

        String content = body.get("content");
        if(content == null || content.isBlank()){
            throw new UnprocessableEntityException("\"content\" cannot be empty");
        }

        PostRequestDTO postDTO = new PostRequestDTO(content);
        Post post = postService.createPost(postDTO);

        return ResponseEntity.ok(Map.of("id", Long.toString(post.getId()),
                "status", Integer.toString(HttpServletResponse.SC_CREATED)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updatePost(@PathVariable Long id, @RequestBody Map<String, String> body){
        if(!body.containsKey("content")){
            throw new BadRequestException("Missing content");
        }

        String content = body.get("content");
        if(content == null || content.isBlank()){
            throw new UnprocessableEntityException("\"content\" cannot be empty");
        }

        postService.updatePost(id, content);
        return ResponseEntity.ok(Map.of("status", Integer.toString(HttpServletResponse.SC_NO_CONTENT)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok(Map.of("status", Integer.toString(HttpServletResponse.SC_NO_CONTENT)));
    }
}
