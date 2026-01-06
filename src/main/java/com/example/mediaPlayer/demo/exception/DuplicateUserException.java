package com.example.mediaPlayer.demo.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String msg) {
        super(msg);
    }
}