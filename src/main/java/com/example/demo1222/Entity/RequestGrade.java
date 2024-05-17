package com.example.demo1222.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "request_grade")
public class RequestGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int grade;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "group_subject_teacher_id")
    private GroupSubjectTeacher groupSubjectTeacher;
}
