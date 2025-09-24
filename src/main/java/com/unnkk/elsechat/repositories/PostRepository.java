package com.unnkk.elsechat.repositories;

import com.unnkk.elsechat.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAuthorId(long authorId);
}
