package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {
    List<Grade> findAllByUserIdAndSemesterIdAndTypeOfGradeIdAndSubjectId(Long userId,Long semesterId,Long typeOfGradeId,Long subjectId);
    @Query(value = "SELECT DISTINCT subject_id FROM grade WHERE users_id = :userId", nativeQuery = true)
    List<Long> findDistinctSubjectIdsByUserId(@Param("userId") Long userId);
}
