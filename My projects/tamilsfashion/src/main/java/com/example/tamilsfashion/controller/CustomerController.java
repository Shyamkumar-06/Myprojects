package com.example.tamilsfashion.controller;

import com.example.tamilsfashion.entity.Customer;
import com.example.tamilsfashion.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    
    @GetMapping
    public String viewCustomers(Model model) {

        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("customer", new Customer());

        return "customer-management";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {

        customerService.saveCustomer(customer);

        return "redirect:/customers";
    }

    
    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {

        Customer customer = customerService.getCustomerById(id);

        model.addAttribute("customer", customer);
        model.addAttribute("customers", customerService.getAllCustomers());

        return "customer-management";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer) {

        customerService.saveCustomer(customer);

        return "redirect:/customers";
    }

    
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return "redirect:/customers";
    }

    
    @GetMapping("/search")
    public String searchCustomer(@RequestParam("keyword") String keyword,
                                 Model model) {

        model.addAttribute("customers",
                customerService.searchCustomers(keyword));

        model.addAttribute("customer", new Customer());

        return "customer-management";
    }

}