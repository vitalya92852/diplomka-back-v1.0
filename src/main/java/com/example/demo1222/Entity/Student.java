package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String surname;
    private int course;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "student")
    public List<AimSubjectStudent> aimSubjectStudents;

    @OneToMany(mappedBy = "student")
    public List<StudentAimNameInfo> studentAimNameInfos;


    @OneToMany(mappedBy = "student")
    public List<RequestGrade> requestGrades;

    @OneToMany(mappedBy = "student")
    public List<StudentAimStatus> studentAimStatuses;

    @OneToMany(mappedBy = "student")
    public List<StudentResume> studentResumes;
}
