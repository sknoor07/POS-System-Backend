package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.User;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.payload.dto.StoreDTO;

public class StoreMapper {
    public static StoreDTO toStoreDTO(Store store) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setBrandName(store.getBrandName());
        storeDTO.setCreatedAt(store.getCreatedAt());
        storeDTO.setUpdatedAt(store.getUpdatedAt());
        storeDTO.setDescription(store.getDescription());
        storeDTO.setStoreType(store.getStoreType());
        storeDTO.setStatus(store.getStatus());
        storeDTO.setContact(store.getContact());
        storeDTO.setStoreAdmin(UserMapper.toDTO(store.getStoreAdmin()));
        return storeDTO;
    }

    public static Store toStoreEntity(StoreDTO storeDTO, User storeAdmin) {
        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setBrandName(storeDTO.getBrandName());
        store.setCreatedAt(storeDTO.getCreatedAt());
        store.setUpdatedAt(storeDTO.getUpdatedAt());
        store.setDescription(storeDTO.getDescription());
        store.setStoreType(storeDTO.getStoreType());
        store.setStatus(storeDTO.getStatus());
        store.setContact(storeDTO.getContact());
        store.setStoreAdmin(storeAdmin);
        return store;
    }
}
