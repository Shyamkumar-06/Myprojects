package com.example.tamilsfashion.controller;

import com.example.tamilsfashion.entity.Admin;
import com.example.tamilsfashion.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private AdminService adminService;

   
    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("admin", new Admin());

        return "register";
    }

    // Save Admin
    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute Admin admin,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {

       
        if (!admin.getPassword().equals(confirmPassword)) {

            model.addAttribute("error", "Password and Confirm Password do not match.");
            model.addAttribute("admin", admin);

            return "register";
        }

        
        if (adminService.usernameExists(admin.getUsername())) {

            model.addAttribute("error", "Username already exists.");
            model.addAttribute("admin", admin);

            return "register";
        }

        admin.setRole("ROLE_ADMIN");

        adminService.registerAdmin(admin);

        model.addAttribute("success", "Registration Successful. Please Login.");

        return "login";
    }

   
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {

        return "forgot-password";
    }

   
    @PostMapping("/forgot-password")
    public String updatePassword(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword,
                                 Model model) {

        if (!password.equals(confirmPassword)) {

            model.addAttribute("error", "Passwords do not match.");

            return "forgot-password";
        }

        boolean updated = adminService.updatePassword(username, password);

        if (!updated) {

            model.addAttribute("error", "Username not found.");

            return "forgot-password";
        }

        model.addAttribute("success", "Password Updated Successfully.");

        return "login";
    }

}