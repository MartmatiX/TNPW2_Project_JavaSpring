package com.tnpw2.project.database_operations;

import com.tnpw2.project.user_objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) return false;
        userRepository.save(user);
        return true;
    }

    public Optional<User> findByUsername(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional;
    }


}
