package com.example.demo1222.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;



    private int age;

    private String gender;

    private int individualidentificationnumber;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Grade> gradeList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Collection<Role> roles;




}
