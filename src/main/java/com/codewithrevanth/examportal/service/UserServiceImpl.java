package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.User;
import com.codewithrevanth.examportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User createUser(User user) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if (local != null) {
            throw new Exception("User already present with this username!");
        } else {
            // Here you can also add password hashing (BCrypt) later!
            local = this.userRepository.save(user);
        }
        return local;
    }


    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }
}