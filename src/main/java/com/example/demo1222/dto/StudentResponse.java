package com.example.demo1222.dto;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String lastname;
    private String surname;
    private String group;
    private int course;
    private int statusGrade;
}
