package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByProductIdAndBranchId(Long productId, Long branchId);

    List<Inventory> findByBranchId(Long branchId);
}
