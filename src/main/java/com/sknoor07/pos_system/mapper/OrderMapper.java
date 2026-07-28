package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.payload.dto.OrderDTO;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .branchId(order.getBranch().getId())
                .cashierDTO(UserMapper.toDTO(order.getCashier()))
                .customer(order.getCustomer())
                .paymentType(order.getPaymentType())
                .orderStatus(order.getOrderStatus())
                .orderItem(order.getOrderItems().stream().map(OrderItemMapper::toDto).collect(Collectors.toList()))
                .build();
    }


}
