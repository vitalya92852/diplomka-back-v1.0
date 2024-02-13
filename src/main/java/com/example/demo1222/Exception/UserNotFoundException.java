package com.example.demo1222.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        System.out.println("Токен истек");
    }
}
