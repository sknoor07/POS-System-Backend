package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByBranchId(Long branchId);
    List<Order> findByCashierId(Long caseId);
    List<Order> findByBranchIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(Long branchId, LocalDateTime from, LocalDateTime to);
    List<Order> findByCashierAndCreatedAtBetween(User cashier, LocalDateTime from, LocalDateTime to);
    List<Order> findTop5ByBranchIdOrderByCreatedAtDesc(Long branchId);



}
