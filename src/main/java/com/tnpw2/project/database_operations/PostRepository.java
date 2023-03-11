package com.tnpw2.project.database_operations;

import com.tnpw2.project.post_objects.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id LIMIT 100", nativeQuery = true)
    List<Post> selectAllPosts();

    @Query(value = "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id WHERE p.user_id = ?", nativeQuery = true)
    List<Post> selectAllUserPosts(Long user_id);

    @Query(value = "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id WHERE p.id = ?", nativeQuery = true)
    List<Post> selectIndividualPost(Long id);

    @Modifying
    @Query(value = "UPDATE Post p SET p.header = :header, p.text = :text WHERE p.id = :id")
    int updatePost(@Param("header") String header, @Param("text") String text, @Param("id") Long id);

}
