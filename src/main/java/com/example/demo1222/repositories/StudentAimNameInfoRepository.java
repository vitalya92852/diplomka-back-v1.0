package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Aim;
import com.example.demo1222.Entity.Student;
import com.example.demo1222.Entity.StudentAimNameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StudentAimNameInfoRepository extends JpaRepository<StudentAimNameInfo,Long> {
    StudentAimNameInfo findByStudentAndAim(Student student, Aim aim);

}
