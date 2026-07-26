package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;

    private BranchDTO branchDto;
    private Long branchId;

    private ProductDTO productDto;
    private Long productId;

    private int quantity;

    private LocalDateTime lastUpdated;

}
