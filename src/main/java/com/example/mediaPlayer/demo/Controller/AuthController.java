package com.example.mediaPlayer.demo.Controller;


import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.ServiceI.AuthServiceInterface;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import com.example.mediaPlayer.demo.dto.request.LoginRequest;
import com.example.mediaPlayer.demo.dto.request.SignupRequest;
import com.example.mediaPlayer.demo.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/authController")
public class AuthController {


     @Autowired
     private AuthServiceInterface authServiceInterface;

     // https://localhost:9090/api/auth/signup

    //https://localhost:9090/api/auth/signup/102/pass   - @pathVariable
    //https://localhost:9090/api/auth/signup/username?102 && password?pass@123 -@RequestParam
    //User : {username - chirag , password : chetana , role : null }   @RestBody


    @PostMapping("/signup")
    public ResponseEntity<ApiSuccessResponse<User>> signup(
            @RequestBody SignupRequest request
    ) {
        User data = authServiceInterface.signup(request);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Signup successfully",
                        data,
                        LocalDateTime.now()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>>  login(
            @RequestBody LoginRequest request
    ) {
        LoginResponse response = authServiceInterface.login(request);
        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Login successful",
                        response,
                        LocalDateTime.now()
                )
        );
    }


    // ✅ LOGOUT API
    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse<String>> logout(
            HttpServletRequest request
    ) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }

        authServiceInterface.logout(authHeader);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Logout successful",
                        "Token expired successfully",
                        LocalDateTime.now()
                )
        );
    }



}
