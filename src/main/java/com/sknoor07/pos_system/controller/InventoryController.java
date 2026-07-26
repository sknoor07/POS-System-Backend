package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.InventoryService;
import com.sknoor07.pos_system.payload.dto.InventoryDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping()
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(inventoryDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.updateInventory(id, inventoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteInventory(@PathVariable Long id) throws Exception {
        inventoryService.deleteInventory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Inventory Deleted success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventoryById(id));
    }

    @GetMapping("/product/{productId}/branch/{branchId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductIdAndBranchId(@PathVariable Long productId, @PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventoryByProductIdAndBranchId(productId, branchId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getAllInventoryByBranchID(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllInventoryByBranchID(branchId));
    }




}
