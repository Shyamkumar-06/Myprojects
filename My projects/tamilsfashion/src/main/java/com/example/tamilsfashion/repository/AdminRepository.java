package com.example.tamilsfashion.repository;

import com.example.tamilsfashion.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find Admin by Username (Login)
    Optional<Admin> findByUsername(String username);

    // Check Username Exists (Registration)
    boolean existsByUsername(String username);

}