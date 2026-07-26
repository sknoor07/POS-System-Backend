package com.sknoor07.pos_system.modals.inventory;

import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private int quantity;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    private void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
