package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.CustomerService;
import com.sknoor07.pos_system.modals.customer.Customer;
import com.sknoor07.pos_system.repository.CustomerRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRespository customerRespository;

    @Override
    public Customer createCustomer(Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        return customerRespository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer customer1=customerRespository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer1.setUpdatedAt(LocalDateTime.now());
        customer1.setFirstName(customer.getFirstName());
        customer1.setLastName(customer.getLastName());
        customer1.setEmail(customer.getEmail());
        customer1.setPhone(customer.getPhone());
        customer1.setDateOfBirth(customer.getDateOfBirth());
        return customerRespository.save(customer1);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer customer1=customerRespository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRespository.delete(customer1);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRespository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRespository.findAll();
    }

    @Override
    public List<Customer> searchCustomer(String Keyword) throws Exception {
        return customerRespository.findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(Keyword, Keyword);
    }
}
