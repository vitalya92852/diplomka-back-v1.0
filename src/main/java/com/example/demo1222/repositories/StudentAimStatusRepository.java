package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Student;
import com.example.demo1222.Entity.StudentAimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAimStatusRepository extends JpaRepository<StudentAimStatus,Long> {
    Optional<StudentAimStatus> findByStudentIdAndAimId(Long studentId, Long aimId);
    StudentAimStatus findByStudentAndAim_Id(Student student,Long aimId);
}
