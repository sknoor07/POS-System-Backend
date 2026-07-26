package com.sknoor07.pos_system.controller;


import com.sknoor07.pos_system.Service.BranchService;
import com.sknoor07.pos_system.payload.dto.BranchDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id,@RequestBody BranchDTO branchDTO) throws Exception {
        BranchDTO updatedBranch= branchService.updateBranch(id, branchDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBranch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) throws Exception {
        BranchDTO branch= branchService.getBranchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(branch);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranches(@PathVariable Long storeId) throws Exception {
        List<BranchDTO> branches= branchService.getAllBranchesByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(branches);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteBranch(@PathVariable Long id) throws Exception {
        branchService.DeleteBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Branch Deleted"));
    }


}
