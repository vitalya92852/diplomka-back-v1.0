package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="groupname")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    public List<Student> studentList;

    @OneToMany(mappedBy = "group")
    public List<GroupSubjectTeacher> GroupSubjectTeachers;

}
