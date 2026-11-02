package com.codewithrevanth.examportal.controller;

import com.codewithrevanth.examportal.config.JwtUtils;
import com.codewithrevanth.examportal.service.UserDetailsServiceImpl;
import com.codewithrevanth.examportal.model.JwtRequest;
import com.codewithrevanth.examportal.model.JwtResponse;
import com.codewithrevanth.examportal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtils jwtUtils;

    // Generate Token Endpoint
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (UsernameNotFoundException e) {
            throw new Exception("User not found: " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials: " + e.getMessage());
        }

        // Authenticated successfully -> Load user and generate token
        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER DISABLED: " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID CREDENTIALS: " + e.getMessage());
        }
    }

    // Get current logged-in user details
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        return (User) this.userDetailsServiceImpl.loadUserByUsername(principal.getName());
    }
}