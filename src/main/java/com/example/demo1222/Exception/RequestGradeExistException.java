package com.example.demo1222.Exception;

public class RequestGradeExistException extends RuntimeException{
    public RequestGradeExistException(String message) {
        super(message);
    }
}
