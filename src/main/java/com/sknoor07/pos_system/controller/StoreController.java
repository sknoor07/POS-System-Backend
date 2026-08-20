package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.StoreService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.mapper.StoreMapper;
import com.sknoor07.pos_system.modals.store.StoreStatus;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.StoreDTO;
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
@RequiredArgsConstructor
@RequestMapping("/api/store")
@Tag(name = "Store Management", description = "Endpoints for registering, managing, retrieving, and moderating stores")
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @Operation(summary = "Create Store", description = "Registers a new store for the authenticated store admin user.")
    @ApiResponse(responseCode = "201", description = "Store successfully created")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping
    public ResponseEntity<StoreDTO> createStore(
            @RequestBody StoreDTO storeDTO,
            @Parameter(description = "Bearer JWT token", required = true) @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        if (user == null) {
            throw new UserException("Invalid token");
        }
        StoreDTO storeDto = storeService.createStore(storeDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(storeDto);
    }

    @Operation(summary = "Get All Stores", description = "Retrieves a list of all registered stores in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved stores list")
    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStore() throws Exception {
        List<StoreDTO> allStores = storeService.getAllStores();
        return ResponseEntity.status(HttpStatus.OK).body(allStores);
    }

    @Operation(summary = "Get Store by ID", description = "Retrieves store details by store ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store details")
    @ApiResponse(responseCode = "404", description = "Store not found")
    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long id) throws Exception {
        StoreDTO storeDto = storeService.getStoreById(id);
        return ResponseEntity.status(HttpStatus.OK).body(storeDto);
    }

    @Operation(summary = "Get Store for Authenticated Admin", description = "Retrieves store profile associated with the currently logged-in store admin.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store profile")
    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin() throws Exception {
        StoreDTO storeDto = StoreMapper.toStoreDTO(storeService.getStoreByAdmin());
        return ResponseEntity.status(HttpStatus.OK).body(storeDto);
    }

    @Operation(summary = "Get Store for Authenticated Employee", description = "Retrieves store details for the currently logged-in employee/cashier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store details")
    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee() throws Exception {
        StoreDTO storeDto = storeService.getStoreByEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(storeDto);
    }

    @Operation(summary = "Update Store", description = "Updates an existing store's profile by store ID.")
    @ApiResponse(responseCode = "200", description = "Store successfully updated")
    @ApiResponse(responseCode = "404", description = "Store not found")
    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long id,
            @RequestBody StoreDTO storeDTO) throws UserException {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.updateStore(id, storeDTO));
    }

    @Operation(summary = "Delete Store", description = "Deletes a store by store ID.")
    @ApiResponse(responseCode = "200", description = "Store successfully deleted")
    @ApiResponse(responseCode = "404", description = "Store not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteStore(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long id) throws UserException {
        storeService.deleteStore(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Store deleted Successfully"));
    }

    @Operation(summary = "Moderate Store Status", description = "Allows Super Admin to update store status (e.g. ACTIVE, INACTIVE, SUSPENDED).")
    @ApiResponse(responseCode = "200", description = "Store status successfully updated")
    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long id,
            @Parameter(description = "Target status (ACTIVE, INACTIVE, SUSPENDED)", required = true) @RequestParam StoreStatus status) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.moderateStore(id, status));
    }
}


