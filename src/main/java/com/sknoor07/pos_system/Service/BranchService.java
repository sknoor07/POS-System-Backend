package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO);

    BranchDTO updateBranch(Long id, BranchDTO branchDTO) throws Exception;

    void DeleteBranch(Long id) throws Exception;

    List<BranchDTO> getAllBranchesByStoreId(Long id);

    BranchDTO getBranchById(Long id) throws Exception;

    

}
