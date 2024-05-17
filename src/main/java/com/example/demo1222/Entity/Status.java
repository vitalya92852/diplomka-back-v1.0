package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "status")
    public List<RequestGrade> requestGrades;

    @OneToMany(mappedBy = "status")
    public List<StudentAimStatus> studentAimStatuses;
}
