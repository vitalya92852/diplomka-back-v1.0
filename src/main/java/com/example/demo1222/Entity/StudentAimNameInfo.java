package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student_aim_name_info")
public class StudentAimNameInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aim_id")
    private Aim aim;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String name;
    private String info;
}
