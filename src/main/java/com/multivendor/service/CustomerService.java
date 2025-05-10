package com.multivendor.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.multivendor.repository.CustomerRepository;
import com.multivendor.model.Customer;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public boolean exists(Integer customerId) {
        // Implementation should check if the customer exists
        return customerRepository.existsById(Long.valueOf(customerId));
    }
}