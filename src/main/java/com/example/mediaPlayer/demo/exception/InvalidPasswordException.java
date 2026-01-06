package com.example.mediaPlayer.demo.exception;


public class InvalidPasswordException  extends RuntimeException {
    public InvalidPasswordException(String message)
    {
        super(message);
    }
}
