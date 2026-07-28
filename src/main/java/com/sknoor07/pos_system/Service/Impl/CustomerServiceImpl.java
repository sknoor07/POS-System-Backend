package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.CustomerService;
import com.sknoor07.pos_system.exceptions.ResourceNotFoundException;
import com.sknoor07.pos_system.modals.customer.Customer;
import com.sknoor07.pos_system.payload.request.CustomerCreateDTO;
import com.sknoor07.pos_system.payload.request.CustomerUpdateDTO;
import com.sknoor07.pos_system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CustomerCreateDTO customerDTO) {
        Customer customer = Customer.builder()
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .dateOfBirth(customerDTO.getDateOfBirth())
                .createdAt(LocalDateTime.now())
                .build();
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerUpdateDTO customerDTO) {
        Customer customer1 = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customer1.setUpdatedAt(LocalDateTime.now());
        customer1.setFirstName(customerDTO.getFirstName());
        customer1.setLastName(customerDTO.getLastName());
        customer1.setEmail(customerDTO.getEmail());
        customer1.setPhone(customerDTO.getPhone());
        customer1.setDateOfBirth(customerDTO.getDateOfBirth());
        return customerRepository.save(customer1);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer customer1 = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer1);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> searchCustomer(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null, empty, or whitespace-only");
        }
        return customerRepository.findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword.trim(), keyword.trim(), pageable);
    }
}
