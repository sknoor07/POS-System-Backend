package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.store.StoreStatus;
import com.sknoor07.pos_system.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO, User user);

    StoreDTO getStoreById(Long id) throws Exception;

    List<StoreDTO> getAllStores();

    Store getStoreByAdmin() throws UserException;

    StoreDTO updateStore(Long id, StoreDTO storeDTO) throws UserException;

    void deleteStore(Long id) throws UserException;

    StoreDTO getStoreByEmployee() throws UserException;

    StoreDTO moderateStore(Long id, StoreStatus status) throws Exception;
}
