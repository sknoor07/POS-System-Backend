package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id) throws Exception;

    Customer getCustomer(Long id);

    List<Customer> getAllCustomers();

    List<Customer> searchCustomer(String Keyword) throws Exception;
}
