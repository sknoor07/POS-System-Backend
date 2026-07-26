package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.inventory.Inventory;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.payload.dto.InventoryDTO;
import com.sknoor07.pos_system.payload.dto.ProductDTO;

import java.time.LocalDateTime;

public class InventoryMapper {

    public static InventoryDTO toInventoryDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .branchDto(inventory.getBranch()!=null?BranchMapper.toBranchDto(inventory.getBranch()):null)
                .quantity(inventory.getQuantity())
                .branchId(inventory.getBranch()!=null?inventory.getBranch().getId():null)
                .productId(inventory.getProduct()!=null?inventory.getProduct().getId():null)
                .productDto(ProductMapper.toProductDTO(inventory.getProduct()))
                .lastUpdated(inventory.getLastUpdated())
                .build();
    }

    public static Inventory toInventory(InventoryDTO inventoryDTO, Branch branch, Product product) {
        return Inventory.builder()
                .product(product)
                .branch(branch)
                .quantity(inventoryDTO.getQuantity())
                .build();
    }
}
