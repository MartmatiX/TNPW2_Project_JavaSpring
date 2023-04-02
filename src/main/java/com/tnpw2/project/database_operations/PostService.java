package com.tnpw2.project.database_operations;

import com.tnpw2.project.post_objects.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Post> getAllPosts(){
        return postRepository.selectAllPosts();
    }

    public List<Post> getAllUserPosts(Long user_id){
        return postRepository.selectAllUserPosts(user_id);
    }

    public List<Post> getIndividualPost(Long id){
        return postRepository.selectIndividualPost(id);
    }

    public int updatePost(String header, String text, Long id){
        return postRepository.updatePost(header, text, id);
    }

    public boolean deletePost(Long id) {
        postRepository.deleteById(id);
        return true;
    }
}
