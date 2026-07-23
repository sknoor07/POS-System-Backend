package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.StoreService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.mapper.StoreMapper;
import com.sknoor07.pos_system.modals.User;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.store.StoreStatus;
import com.sknoor07.pos_system.payload.dto.StoreDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        if (user == null) {
             throw new UserException("Invalid token");
        }
        StoreDTO storeDto = storeService.createStore(storeDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(storeDto);
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStore() throws Exception {
        List<StoreDTO> allStores = storeService.getAllStores();
        return ResponseEntity.status(HttpStatus.OK).body(allStores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id) throws Exception {
        StoreDTO storeDto = storeService.getStoreById(id);
        return ResponseEntity.status(HttpStatus.OK).body(storeDto);
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin() throws Exception {
        StoreDTO storeDto = StoreMapper.toStoreDTO(storeService.getStoreByAdmin());
        return ResponseEntity.status(HttpStatus.OK).body(storeDto);
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee() throws Exception {
        StoreDTO storeDto = storeService.getStoreByEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(storeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id,@RequestBody StoreDTO storeDTO) throws UserException {
        return  ResponseEntity.status(HttpStatus.OK).body(storeService.updateStore(id, storeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteStore(@PathVariable Long id) throws UserException {
        storeService.deleteStore(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResposne("Store deleted Successfully"));
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore(@PathVariable Long id, @RequestParam StoreStatus status) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(storeService.moderateStore(id, status));
    }

}


