package com.tnpw2.project.database_operations;

import com.tnpw2.project.post_objects.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM posts LIMIT 5", nativeQuery = true)
    List<Post> selectAllPosts();

}
