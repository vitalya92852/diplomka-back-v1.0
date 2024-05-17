package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Grade;
import com.example.demo1222.Entity.GroupSubjectTeacher;
import com.example.demo1222.Entity.RequestGrade;
import com.example.demo1222.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestGradeRepository extends JpaRepository<RequestGrade,Long> {
    Optional <RequestGrade> findByStudentAndGradeAndGroupSubjectTeacher(Student student, int grade, GroupSubjectTeacher groupSubjectTeacher);
    RequestGrade findByStudentAndGroupSubjectTeacherAndStatusId(Student student,GroupSubjectTeacher groupSubjectTeacher,Long statusId);

    @Query("SELECT r FROM RequestGrade r JOIN r.groupSubjectTeacher gst WHERE gst.teacher.id = :teacherId and r.status.id= :statusId")
    List<RequestGrade> findAllByTeacherIdaAndStatusId(@Param("teacherId") Long teacherId,Long statusId);

    @Query("SELECT r FROM RequestGrade r JOIN r.groupSubjectTeacher gst WHERE gst.teacher.id = :teacherId and r.student.id = :studentId and r.grade = :gradeRequest and gst.subject.name = :subjectName")
    RequestGrade findByTeacherIdAndStudentIdAndGrade(@Param("teacherId") Long teacherId,Long studentId,int gradeRequest,String subjectName);
}
