package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.Service.StoreService;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.payload.dto.BranchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class BranchMapper {

    private final StoreService storeService;
    public  static BranchDTO toBranchDto(Branch branch) {
        BranchDTO branchDTO= BranchDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .workingDays(branch.getWorkingDays())
                .openTime(branch.getOpenTime())
                .closeTime(branch.getCloseTime())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .storeId(branch.getStore()!=null?branch.getStore().getId():null)
                .build();
        return branchDTO;
    }

    public  static Branch toBranch(BranchDTO branchDTO, Store store) {
        return Branch.builder()
                .name(branchDTO.getName())
                .address(branchDTO.getAddress())
                .phone(branchDTO.getPhone())
                .email(branchDTO.getEmail())
                .workingDays(branchDTO.getWorkingDays())
                .openTime(branchDTO.getOpenTime())
                .closeTime(branchDTO.getCloseTime())
                .createdAt(branchDTO.getCreatedAt())
                .updatedAt(branchDTO.getUpdatedAt())
                .store(store)
                .build();
    }
}
