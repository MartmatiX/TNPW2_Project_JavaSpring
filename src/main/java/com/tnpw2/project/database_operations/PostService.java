package com.tnpw2.project.database_operations;

import com.tnpw2.project.post_objects.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean createPost(Post post){
        postRepository.save(post);
        return true;
    }
}