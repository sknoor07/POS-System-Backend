package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.BranchService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.mapper.BranchMapper;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.BranchDTO;
import com.sknoor07.pos_system.repository.BranchRepository;
import com.sknoor07.pos_system.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {
        User currentUser= userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());
        Branch branch = BranchMapper.toBranch(branchDTO,store);
        if (store == null) {
            throw new IllegalStateException("Current user is not assigned to a store");
        }
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toBranchDto(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) throws Exception {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(()-> new Exception("No Matching Branch to update"));
        existingBranch.setName(branchDTO.getName());
        existingBranch.setWorkingDays(branchDTO.getWorkingDays());
        existingBranch.setEmail(branchDTO.getEmail());
        existingBranch.setAddress(branchDTO.getAddress());
        existingBranch.setPhone(branchDTO.getPhone());
        existingBranch.setOpenTime(branchDTO.getOpenTime());
        existingBranch.setCloseTime(branchDTO.getCloseTime());
        existingBranch.setUpdatedAt(LocalDateTime.now());

        Branch savedBranch = branchRepository.save(existingBranch);
        return BranchMapper.toBranchDto(savedBranch);
    }

    @Override
    public void DeleteBranch(Long id) throws Exception {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(()-> new Exception("No Matching Branch to delete"));
        branchRepository.delete(existingBranch);
    }

    @Override
    public List<BranchDTO> getAllBranchesByStoreId(Long storeId) {
        return branchRepository.findByStoreId(storeId).stream().map(BranchMapper::toBranchDto).collect(Collectors.toList());
    }

    @Override
    public BranchDTO getBranchById(Long id) throws Exception {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(()-> new Exception("No Matching Branch Found"));
        return BranchMapper.toBranchDto(existingBranch);
    }
}
