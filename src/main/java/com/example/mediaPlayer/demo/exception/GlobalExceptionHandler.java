package com.example.mediaPlayer.demo.exception;

import com.example.mediaPlayer.demo.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex)
    {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value() ,
                ex.getMessage(),
                LocalDateTime.now()
                );

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return new ResponseEntity<>(
                new ApiErrorResponse(
                        false,
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleAdminExists(AdminAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(
                ApiErrorResponse.builder()
                        .status(400)
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateUser(DuplicateUserException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleMessageNotFound(
            MessageNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.NOT_FOUND
        );
    }



    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        return new ResponseEntity<>(
                new ApiErrorResponse(
                        false,
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request) {

        return new ResponseEntity<>(
                new ApiErrorResponse(false, ex.getMessage(),
                        request.getRequestURI(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }



}
