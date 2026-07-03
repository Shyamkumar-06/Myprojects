package com.example.tamilsfashion.service;

import com.example.tamilsfashion.entity.Customer;
import com.example.tamilsfashion.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

   
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository
                .findByCustomerNameContainingIgnoreCase(keyword);
    }

}