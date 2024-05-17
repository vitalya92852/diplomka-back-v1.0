package com.example.demo1222.dto.teacher;

import lombok.Data;

@Data
public class ChangeGradeRequest {
    private Long userId;
    private Long studentId;
    private String subjectName;
    private int requestGrade;
}
