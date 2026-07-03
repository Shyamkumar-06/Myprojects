package com.example.tamilsfashion.controller;

import com.example.tamilsfashion.repository.BillRepository;
import com.example.tamilsfashion.repository.CustomerRepository;
import com.example.tamilsfashion.repository.ProductRepository;
import com.example.tamilsfashion.service.BillingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillingService billingService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long totalProducts = productRepository.count();
        long totalCustomers = customerRepository.count();
        long totalBills = billRepository.count();

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("totalBills", totalBills);
        model.addAttribute("todaySales", billingService.getTodaySales());

        return "dashboard";
    }

}