package com.example.demo1222.dto;

import lombok.Data;

@Data
public class GradeRequest {
    private Long userId;
    private int grade;
    private String subjectName;
}
