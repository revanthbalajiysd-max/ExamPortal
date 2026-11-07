package com.codewithrevanth.examportal.config;

import com.codewithrevanth.examportal.model.User;
import com.codewithrevanth.examportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("Revanth") == null) {
            User user = new User();
            user.setUsername("Revanth");
            user.setPassword(passwordEncoder.encode("revanth1981"));
            user.setFirstName("Revanth");
            user.setLastName("Balaji");
            user.setEmail("revanth@gmail.com");
            user.setPhone("1234567890");
            user.setProfile("default.png");
            user.setEnabled(true);
            user.setRole("ADMIN"); // Assigning the required role

            userRepository.save(user);
            System.out.println("Default user 'Revanth' created successfully with role and BCrypt encoding!");
        } else {
            // If user already exists without a role, update it
            User user = userRepository.findByUsername("Revanth");
            user.setPassword(passwordEncoder.encode("revanth1981"));
            user.setRole("ADMIN");
            userRepository.save(user);
        }
    }
}