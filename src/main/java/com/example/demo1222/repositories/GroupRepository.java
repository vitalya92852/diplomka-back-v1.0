package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    Group findByName(String name);


}
