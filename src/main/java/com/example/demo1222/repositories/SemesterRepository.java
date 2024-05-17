package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester,Long> {
    Semester findByCount(int count);
}
