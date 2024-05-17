package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student_aim_status")
public class StudentAimStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "aim_id")
    private Aim aim;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
