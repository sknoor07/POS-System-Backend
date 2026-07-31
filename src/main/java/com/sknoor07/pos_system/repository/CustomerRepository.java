package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
    
    Page<Customer> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
}
