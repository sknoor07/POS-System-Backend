package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.InventoryService;
import com.sknoor07.pos_system.payload.dto.InventoryDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
@Tag(name = "Inventory Management", description = "Endpoints for managing branch stock levels, updating inventory, and stock queries")
public class InventoryController {
    private final InventoryService inventoryService;

    @Operation(summary = "Create Inventory", description = "Initializes an inventory record for a product at a branch.")
    @ApiResponse(responseCode = "201", description = "Inventory record successfully created")
    @PostMapping()
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(inventoryDTO));
    }

    @Operation(summary = "Update Inventory", description = "Updates stock quantities or alert thresholds for an inventory record.")
    @ApiResponse(responseCode = "200", description = "Inventory record successfully updated")
    @ApiResponse(responseCode = "404", description = "Inventory record not found")
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(
            @Parameter(description = "ID of the inventory record", required = true) @PathVariable Long id,
            @RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.updateInventory(id, inventoryDTO));
    }

    @Operation(summary = "Delete Inventory", description = "Deletes an inventory record by ID.")
    @ApiResponse(responseCode = "200", description = "Inventory record successfully deleted")
    @ApiResponse(responseCode = "404", description = "Inventory record not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteInventory(
            @Parameter(description = "ID of the inventory record", required = true) @PathVariable Long id) throws Exception {
        inventoryService.deleteInventory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Inventory Deleted success"));
    }

    @Operation(summary = "Get Inventory by ID", description = "Retrieves inventory record details by inventory ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory record")
    @ApiResponse(responseCode = "404", description = "Inventory record not found")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(
            @Parameter(description = "ID of the inventory record", required = true) @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventoryById(id));
    }

    @Operation(summary = "Get Inventory by Product and Branch", description = "Retrieves the inventory stock level for a specific product at a specific branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory stock level")
    @ApiResponse(responseCode = "404", description = "Product or branch inventory not found")
    @GetMapping("/product/{productId}/branch/{branchId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductIdAndBranchId(
            @Parameter(description = "ID of the product", required = true) @PathVariable Long productId,
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventoryByProductIdAndBranchId(productId, branchId));
    }

    @Operation(summary = "Get All Inventories by Branch ID", description = "Retrieves all product inventory records associated with a branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch inventory list")
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getAllInventoryByBranchID(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllInventoryByBranchID(branchId));
    }
}
