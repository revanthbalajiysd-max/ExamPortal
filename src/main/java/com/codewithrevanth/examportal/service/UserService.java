package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.User;

public interface UserService {

    User createUser(User user) throws Exception;

    User getUser(String username);

    void deleteUser(Long userId);
}