package com.example.demo1222.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationUserDto {
    private final String login;
    private final String password;
    private final String confirmPassword;
    private final String name;
    private final String lastname;
    private final String surname;
    private final String group;
    private final int course;

}
