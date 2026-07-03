package com.example.tamilsfashion.service;

import com.example.tamilsfashion.entity.Admin;
import com.example.tamilsfashion.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found"));

        return new User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(admin.getRole())
                )
        );
    }

}