package com.example.demo1222.repositories;

import com.example.demo1222.Entity.Role;
import com.example.demo1222.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    List<UserRole> findAllByUserId(Long userId);
}
