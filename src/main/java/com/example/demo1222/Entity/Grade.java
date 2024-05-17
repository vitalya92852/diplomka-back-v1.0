package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "type_of_grade_id")
    private TypeOfGrade typeOfGrade;

    private int grade;

    private int week;


}
