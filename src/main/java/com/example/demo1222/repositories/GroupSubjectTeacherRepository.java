package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Group;
import com.example.demo1222.Entity.GroupSubjectTeacher;
import com.example.demo1222.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupSubjectTeacherRepository extends JpaRepository<GroupSubjectTeacher,Long> {
    List<GroupSubjectTeacher> findAllByTeacherIdAndSubjectName(Long id,String name);
    List<GroupSubjectTeacher> findAllByTeacherId(Long id);

    GroupSubjectTeacher findByGroupIdAndSubjectId(Long groupId,Long subjectId);
}
