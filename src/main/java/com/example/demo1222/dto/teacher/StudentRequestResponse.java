package com.example.demo1222.dto.teacher;

import lombok.Data;

@Data
public class StudentRequestResponse {
    private Long id;
    private String name;
    private String lastname;
    private String surname;
    private String group;
    private int statusGrade;
    private int course;
    private int requestGrade;
    private String statusName;
}
