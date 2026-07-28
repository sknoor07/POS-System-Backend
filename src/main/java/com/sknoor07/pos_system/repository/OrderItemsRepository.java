package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
}
