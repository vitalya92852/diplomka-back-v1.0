package com.example.demo1222.dto;

import lombok.Data;

@Data
public class JwtSignInRequest {
    private String username;
    private String password;
}
