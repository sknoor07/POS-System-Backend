package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRespository extends JpaRepository<Customer,Long> {

    List<Customer> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}
