package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
    
    Page<Customer> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    @Query("""
        select count(distinct o.customer.id) from Order o
        where o.branch.store.storeAdmin.id=:storeAdminId
""")
    int countByStoreAdminId(@Param("storeAdminId") Long storeAdminId);
}
