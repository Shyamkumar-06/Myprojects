package com.example.tamilsfashion.service;

import com.example.tamilsfashion.entity.Admin;
import com.example.tamilsfashion.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   
    public void registerAdmin(Admin admin) {

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminRepository.save(admin);
    }

    
    public boolean usernameExists(String username) {

        return adminRepository.existsByUsername(username);
    }

    
    public boolean updatePassword(String username, String newPassword) {

        Admin admin = adminRepository.findByUsername(username).orElse(null);

        if (admin == null) {
            return false;
        }

        admin.setPassword(passwordEncoder.encode(newPassword));

        adminRepository.save(admin);

        return true;
    }

}