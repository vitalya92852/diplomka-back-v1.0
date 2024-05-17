package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "aim")
@Data
public class Aim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "aim")
    public List<StudentAimStatus> studentAimStatuses;

    @OneToMany(mappedBy = "aim")
    public List<StudentAimNameInfo> studentAimNameInfos;
}
