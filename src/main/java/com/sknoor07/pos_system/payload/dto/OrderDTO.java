package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.customer.Customer;
import com.sknoor07.pos_system.modals.orders.OrderItem;
import com.sknoor07.pos_system.modals.orders.OrderStatus;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.modals.user.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private BranchDTO branchDTO;
    private Long branchId;

    private UserDTO cashierDTO;

    private Long customerId;

    private Customer customer;
    private PaymentType paymentType;

    private OrderStatus orderStatus;

    private List<OrderItemDTO> orderItem;
}
