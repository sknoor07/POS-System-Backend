package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.BranchService;
import com.sknoor07.pos_system.payload.dto.BranchDTO;
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
@RequestMapping("/api/branch")
@RequiredArgsConstructor
@Tag(name = "Branch Management", description = "Endpoints for creating, updating, retrieving, listing, and deleting store branches")
public class BranchController {

    private final BranchService branchService;

    @Operation(summary = "Create Branch", description = "Creates a new branch associated with a specific store.")
    @ApiResponse(responseCode = "201", description = "Branch successfully created")
    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    @Operation(summary = "Update Branch", description = "Updates an existing branch's details by branch ID.")
    @ApiResponse(responseCode = "200", description = "Branch successfully updated")
    @ApiResponse(responseCode = "404", description = "Branch not found")
    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long id,
            @RequestBody BranchDTO branchDTO) throws Exception {
        BranchDTO updatedBranch = branchService.updateBranch(id, branchDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBranch);
    }

    @Operation(summary = "Get Branch by ID", description = "Retrieves branch details by branch ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch details")
    @ApiResponse(responseCode = "404", description = "Branch not found")
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long id) throws Exception {
        BranchDTO branch = branchService.getBranchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(branch);
    }

    @Operation(summary = "Get All Branches by Store ID", description = "Retrieves a list of all branches belonging to a specific store.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch list")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranches(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long storeId) throws Exception {
        List<BranchDTO> branches = branchService.getAllBranchesByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(branches);
    }

    @Operation(summary = "Delete Branch", description = "Deletes a branch by branch ID.")
    @ApiResponse(responseCode = "200", description = "Branch successfully deleted")
    @ApiResponse(responseCode = "404", description = "Branch not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteBranch(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long id) throws Exception {
        branchService.DeleteBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Branch Deleted"));
    }
}
