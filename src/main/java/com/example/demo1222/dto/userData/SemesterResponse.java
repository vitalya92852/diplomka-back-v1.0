package com.example.demo1222.dto.userData;

import lombok.Data;

@Data
public class SemesterResponse {
    private int semesterCount;

    private WeekResponse[] week;
}
