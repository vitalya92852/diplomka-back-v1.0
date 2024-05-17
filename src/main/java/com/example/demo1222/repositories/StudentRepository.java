package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByUserId(Long id);
    List<Student> findAllByGroupId(Long id);


}
