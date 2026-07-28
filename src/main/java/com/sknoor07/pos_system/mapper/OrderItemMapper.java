package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.orders.OrderItem;
import com.sknoor07.pos_system.payload.dto.OrderItemDTO;

public class OrderItemMapper {

    public static OrderItemDTO toDto(OrderItem orderItem) {
        if(orderItem == null) return null;
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .productId(orderItem.getProduct().getId())
                .orderId(orderItem.getOrder().getId())
                .build();
    }
}
