package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.customer.Customer;
import com.sknoor07.pos_system.payload.request.CustomerCreateDTO;
import com.sknoor07.pos_system.payload.request.CustomerUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CustomerCreateDTO customerDTO);
    Customer updateCustomer(Long id, CustomerUpdateDTO customerDTO);
    void deleteCustomer(Long id) throws Exception;

    Customer getCustomer(Long id);

    List<Customer> getAllCustomers();

    Page<Customer> searchCustomer(String keyword, Pageable pageable);
}
