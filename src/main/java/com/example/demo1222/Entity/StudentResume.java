package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student_resume")
public class StudentResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String path;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
