package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Aim;
import com.example.demo1222.Entity.AimSubjectStudent;
import com.example.demo1222.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AimSubjectStudentRepository extends JpaRepository<AimSubjectStudent,Long> {
    List<AimSubjectStudent> findAllByStudent(Student student);
    List<AimSubjectStudent> findAllByAimId(Long id);
}
