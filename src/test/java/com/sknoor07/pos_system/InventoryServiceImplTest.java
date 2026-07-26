package com.sknoor07.pos_system;

import com.sknoor07.pos_system.Service.Impl.InventoryServiceImpl;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.inventory.Inventory;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.payload.dto.InventoryDTO;
import com.sknoor07.pos_system.repository.BranchRepository;
import com.sknoor07.pos_system.repository.InventoryRepository;
import com.sknoor07.pos_system.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Branch branch;
    private Product product;
    private InventoryDTO inventoryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        branch = new Branch();
        branch.setId(1L);

        product = new Product();
        product.setId(2L);

        inventoryDTO = new InventoryDTO();
        inventoryDTO.setBranchId(1L);
        inventoryDTO.setProductId(2L);
        inventoryDTO.setQuantity(10);
    }

    @Test
    void createInventory_NewRecord_Success() throws Exception {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductIdAndBranchId(2L, 1L)).thenReturn(null);

        Inventory savedInventory = new Inventory();
        savedInventory.setId(100L);
        savedInventory.setBranch(branch);
        savedInventory.setProduct(product);
        savedInventory.setQuantity(10);

        when(inventoryRepository.saveAndFlush(any(Inventory.class))).thenReturn(savedInventory);

        InventoryDTO result = inventoryService.createInventory(inventoryDTO);

        assertNotNull(result);
        assertEquals(10, result.getQuantity());
        verify(inventoryRepository, times(1)).saveAndFlush(any(Inventory.class));
    }

    @Test
    void createInventory_ExistingRecord_UpdatesQuantity() throws Exception {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        Inventory existingInventory = new Inventory();
        existingInventory.setId(100L);
        existingInventory.setBranch(branch);
        existingInventory.setProduct(product);
        existingInventory.setQuantity(15);

        when(inventoryRepository.findByProductIdAndBranchId(2L, 1L)).thenReturn(existingInventory);

        Inventory updatedInventory = new Inventory();
        updatedInventory.setId(100L);
        updatedInventory.setBranch(branch);
        updatedInventory.setProduct(product);
        updatedInventory.setQuantity(25);

        when(inventoryRepository.save(existingInventory)).thenReturn(updatedInventory);

        InventoryDTO result = inventoryService.createInventory(inventoryDTO);

        assertNotNull(result);
        assertEquals(25, result.getQuantity());
        verify(inventoryRepository, times(1)).save(existingInventory);
        verify(inventoryRepository, never()).saveAndFlush(any(Inventory.class));
    }

    @Test
    void createInventory_ConcurrentConflict_UpdatesQuantity() throws Exception {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        // Simulating that pre-insertion check returns null (not found yet)
        when(inventoryRepository.findByProductIdAndBranchId(2L, 1L)).thenReturn(null);

        // Simulate unique constraint violation during flush
        when(inventoryRepository.saveAndFlush(any(Inventory.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate key"));

        Inventory existingInventory = new Inventory();
        existingInventory.setId(100L);
        existingInventory.setBranch(branch);
        existingInventory.setProduct(product);
        existingInventory.setQuantity(15);

        // On fallback search, the existing record is found
        when(inventoryRepository.findByProductIdAndBranchId(2L, 1L)).thenReturn(existingInventory);

        Inventory updatedInventory = new Inventory();
        updatedInventory.setId(100L);
        updatedInventory.setBranch(branch);
        updatedInventory.setProduct(product);
        updatedInventory.setQuantity(25);

        when(inventoryRepository.save(existingInventory)).thenReturn(updatedInventory);

        InventoryDTO result = inventoryService.createInventory(inventoryDTO);

        assertNotNull(result);
        assertEquals(25, result.getQuantity());
        verify(inventoryRepository, times(1)).save(existingInventory);
    }
}
