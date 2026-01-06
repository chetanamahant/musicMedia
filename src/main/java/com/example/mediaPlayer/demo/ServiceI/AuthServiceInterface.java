package com.example.mediaPlayer.demo.ServiceI;


import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.dto.request.LoginRequest;
import com.example.mediaPlayer.demo.dto.request.SignupRequest;
import com.example.mediaPlayer.demo.dto.response.LoginResponse;

public interface AuthServiceInterface {

    User signup(SignupRequest request);
    LoginResponse login(LoginRequest request);

    void logout(String authHeader);
}
