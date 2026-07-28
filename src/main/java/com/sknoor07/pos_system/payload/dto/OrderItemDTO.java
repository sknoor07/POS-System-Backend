package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.product.Product;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;

    private Integer quantity;

    private BigDecimal price;

    private Long productId;
    private Long orderId;

}
