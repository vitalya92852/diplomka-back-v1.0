package com.example.demo1222.repositories;

import com.example.demo1222.Entity.StudentResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentResumeRepository extends JpaRepository<StudentResume,Long> {

}
