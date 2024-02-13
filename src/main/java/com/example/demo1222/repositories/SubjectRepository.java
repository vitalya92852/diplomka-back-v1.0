package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Subject findSubjectByName(String name);
}
