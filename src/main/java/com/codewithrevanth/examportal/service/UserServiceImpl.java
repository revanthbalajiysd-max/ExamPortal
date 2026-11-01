package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.User;
import com.codewithrevanth.examportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if (local != null) {
            throw new Exception("User already present with this username!");
        } else {
            // 2. Encode the password using BCrypt before saving to database
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
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