package com.example.demo1222.repositories;

import com.example.demo1222.Entity.TypeOfGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfGradeRepository extends JpaRepository<TypeOfGrade,Long> {
}
