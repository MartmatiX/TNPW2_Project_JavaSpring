package com.tnpw2.project.database_operations;

import com.tnpw2.project.post_objects.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {



}
