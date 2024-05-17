package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Aim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AimRepository extends JpaRepository<Aim,Long> {
    Aim findByName(String name);

}
