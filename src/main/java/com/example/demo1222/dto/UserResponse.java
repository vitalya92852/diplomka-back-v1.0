package com.example.demo1222.dto;


import lombok.Data;


@Data
public class UserResponse {
    private long id;
    private String name;
    private String token;
    private String[] role;
}
