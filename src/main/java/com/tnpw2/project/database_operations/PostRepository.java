package com.tnpw2.project.database_operations;

import com.tnpw2.project.post_objects.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id LIMIT 100", nativeQuery = true)
    List<Post> selectAllPosts();

    @Query(value = "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id WHERE p.user_id = ?", nativeQuery = true)
    List<Post> selectAllUserPosts(Long user_id);

}
