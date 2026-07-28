package com.sknoor07.pos_system.controller;


import com.sknoor07.pos_system.Service.CustomerService;
import com.sknoor07.pos_system.modals.customer.Customer;
import com.sknoor07.pos_system.payload.request.CustomerCreateDTO;
import com.sknoor07.pos_system.payload.request.CustomerUpdateDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerCreateDTO customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,@Valid @RequestBody CustomerUpdateDTO customer) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id,customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteCustomer(@PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Customer Deleted Successfully"));
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> getAllCustomer() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Customer>> setCustomer(@RequestParam String keyword, Pageable pageable) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.searchCustomer(keyword,pageable));
    }



}
