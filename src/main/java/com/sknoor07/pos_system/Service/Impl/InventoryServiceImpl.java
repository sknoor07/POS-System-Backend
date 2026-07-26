package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.InventoryService;
import com.sknoor07.pos_system.mapper.InventoryMapper;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.inventory.Inventory;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.payload.dto.InventoryDTO;
import com.sknoor07.pos_system.repository.BranchRepository;
import com.sknoor07.pos_system.repository.InventoryRepository;
import com.sknoor07.pos_system.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception {
        Branch branch = branchRepository.findById(inventoryDTO.getBranchId()).orElseThrow(()-> new Exception("Branch not found..."));
        Product product = productRepository.findById(inventoryDTO.getProductId()).orElseThrow(()-> new Exception("Product not found..."));
        if(branch ==null || product ==null){
            throw new Exception("Branch or Product not found...");
        }
        
        Inventory existing = inventoryRepository.findByProductIdAndBranchId(product.getId(), branch.getId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + inventoryDTO.getQuantity());
            return InventoryMapper.toInventoryDTO(inventoryRepository.save(existing));
        }

        Inventory inventory= InventoryMapper.toInventory(inventoryDTO,branch,product);
        inventory.setBranch(branch);

        try {
            return InventoryMapper.toInventoryDTO(inventoryRepository.saveAndFlush(inventory));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            Inventory concurrentExisting = inventoryRepository.findByProductIdAndBranchId(product.getId(), branch.getId());
            if (concurrentExisting != null) {
                concurrentExisting.setQuantity(concurrentExisting.getQuantity() + inventoryDTO.getQuantity());
                return InventoryMapper.toInventoryDTO(inventoryRepository.save(concurrentExisting));
            }
            throw new Exception("Concurrent conflict when saving inventory record", e);
        }
    }

    @Override
    public InventoryDTO updateInventory(Long id,InventoryDTO inventoryDTO) throws Exception {
        Inventory inventory=inventoryRepository.findById(id).orElseThrow(()-> new Exception("Inventory Not Found... "));
        inventory.setQuantity(inventoryDTO.getQuantity());
        return InventoryMapper.toInventoryDTO(inventoryRepository.save(inventory));
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory=inventoryRepository.findById(id).orElseThrow(()-> new Exception("Inventory Not Found... "));
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long id) throws Exception {
        Inventory inventory=inventoryRepository.findById(id).orElseThrow(()-> new Exception("Inventory Not Found... "));
        return InventoryMapper.toInventoryDTO(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) throws Exception {
        Inventory inventory=inventoryRepository.findByProductIdAndBranchId(productId,branchId);
        if(inventory==null){
            throw new Exception("Inventory not found");
        }
        return InventoryMapper.toInventoryDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranchID(Long branchId) {
        return inventoryRepository.findByBranchId(branchId).stream().map(InventoryMapper::toInventoryDTO).collect(Collectors.toList());
    }
}
