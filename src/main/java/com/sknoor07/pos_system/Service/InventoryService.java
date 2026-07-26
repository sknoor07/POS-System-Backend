package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.inventory.Inventory;
import com.sknoor07.pos_system.payload.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception;

    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) throws Exception;

    void deleteInventory(Long id) throws Exception;

    InventoryDTO getInventoryById (Long id) throws Exception;

    InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) throws Exception;

    List<InventoryDTO> getAllInventoryByBranchID(Long branchId);
}
