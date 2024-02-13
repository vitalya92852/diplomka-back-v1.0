package com.example.demo1222.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtSignInResponse {
    private String token;
}
