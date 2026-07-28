package com.sknoor07.pos_system.modals.orders;

import com.sknoor07.pos_system.modals.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer quantity;

    private BigDecimal price;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

}
