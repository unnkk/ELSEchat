package com.unnkk.elsechat.services;

import com.unnkk.elsechat.DTOs.PostRequestDTO;
import com.unnkk.elsechat.DTOs.PostResponseDTO;
import com.unnkk.elsechat.entities.Post;
import com.unnkk.elsechat.entities.User;
import com.unnkk.elsechat.exceptions.NotFoundException;
import com.unnkk.elsechat.repositories.PostRepository;
import com.unnkk.elsechat.utils.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public Post createPost(PostRequestDTO postDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByUsername(authentication.getName())
                .orElseThrow(() -> new InternalError("Failed to get user id"));

        Post post = new Post();
        post.setAuthor(currentUser);
        post.setContent(postDTO.content());
        postRepository.save(post);
        return post;
    }

    public List<PostResponseDTO> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toResponseDTO)
                .toList();
    }

    public Post getPostById(long id){
        return postRepository.findById(id).orElse(null);
    }

    public List<PostResponseDTO> getPostsByAuthor(long authorId){
        if(userService.getUserById(authorId).isPresent()){
            return postRepository.findByAuthorId(authorId)
                    .stream()
                    .map(PostMapper::toResponseDTO)
                    .toList();
        }else{
            throw new NotFoundException("Cannot find author with id " + authorId);
        }
    }

    public void updatePost(long id, String content){
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find post with id " + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByUsername(authentication.getName())
                .orElseThrow(() -> new InternalError("Failed to get user id"));

        if(post.getAuthor().equals(currentUser)){
            post.setContent(content);
        }else {
            throw new AccessDeniedException("You cannot edit others posts");
        }
    }

    public void deletePost(long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find post with id " + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByUsername(authentication.getName())
                .orElseThrow(() -> new InternalError("Failed to get user id"));

        if(post.getAuthor().equals(currentUser)){
            postRepository.delete(post);
        }else {
            throw new AccessDeniedException("You cannot delete others posts");
        }
    }
}
