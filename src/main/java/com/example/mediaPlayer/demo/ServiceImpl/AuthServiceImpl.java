package com.example.mediaPlayer.demo.ServiceImpl;


import com.example.mediaPlayer.demo.Entity.BlacklistedToken;
import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.Repo.BlacklistedTokenRepository;
import com.example.mediaPlayer.demo.Repo.UserRepo;
import com.example.mediaPlayer.demo.ServiceI.AuthServiceInterface;
import com.example.mediaPlayer.demo.dto.request.LoginRequest;
import com.example.mediaPlayer.demo.dto.request.SignupRequest;

import com.example.mediaPlayer.demo.dto.response.LoginResponse;
import com.example.mediaPlayer.demo.exception.AdminAlreadyExistsException;
import com.example.mediaPlayer.demo.exception.DuplicateUserException;
import com.example.mediaPlayer.demo.exception.InvalidPasswordException;
import com.example.mediaPlayer.demo.exception.UserNotFoundException;
import com.example.mediaPlayer.demo.util.JwtUtil;
import com.example.mediaPlayer.demo.util.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthServiceInterface {


     @Autowired
     private UserRepo userRepo;

     @Autowired
     private BlacklistedTokenRepository blacklistedTokenRepository;

     private PasswordEncoder passwordEncoder;

     public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
     private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper mapper;


    @Override
    public User signup(SignupRequest request) {

        // 🔴 Username check
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateUserException("Username already exists");
        }

        // 🔴 Email check
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateUserException("Email already exists");
        }

        // 🔐 Normalize role input
        String requestedRole = request.getRole() == null
                ? "USER"
                : request.getRole().toUpperCase();

        if (requestedRole.equals("ADMIN")) {
            boolean adminExists = userRepo.existsByRole(Role.ADMIN);
            if (adminExists) {
                throw new AdminAlreadyExistsException(
                        "Admin already exists. Only one admin is allowed."
                );
            }
        }

        // ✅ Map request → entity
        User user = mapper.map(request, User.class);

        // 🔐 Secure password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 🔐 Set validated role
        user.setRole(Role.valueOf(requestedRole));

        return userRepo.save(user);
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        // Optional<User> existingUser = userRepo.findByUsername(request.getUsername());
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        // ✅ THIS IS THE KEY FIX
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Password does not match");
        }

        String token = jwtUtil.generateToken(user.getUsername());


        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(String.valueOf(user.getRole()))
                .build();
    }


    @Override
    public void logout(String token) {

        // Remove "Bearer "
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Already logged out
        if (blacklistedTokenRepository.existsByToken(token)) {
            return;
        }

        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .blacklistedAt(LocalDateTime.now())
                .build();

        blacklistedTokenRepository.save(blacklistedToken);
    }
}
