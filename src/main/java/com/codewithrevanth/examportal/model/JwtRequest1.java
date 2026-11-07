package com.codewithrevanth.examportal.model;

public class JwtRequest1 {
    private String username;
    private String password;

    public JwtRequest1() {
    }

    public JwtRequest1(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}