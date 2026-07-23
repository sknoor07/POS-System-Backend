package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.StoreService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.mapper.StoreMapper;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.store.StoreContact;
import com.sknoor07.pos_system.modals.store.StoreStatus;
import com.sknoor07.pos_system.payload.dto.StoreDTO;
import com.sknoor07.pos_system.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreSeriveImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        Store store = StoreMapper.toStoreEntity(storeDTO,user);
        return StoreMapper.toStoreDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long id) throws Exception {
        return StoreMapper.
                toStoreDTO(
                        storeRepository
                                .findById(id)
                                .orElseThrow(
                                        ()->new Exception("INVALID ID, STORE NOT FOUND")
                                )
                );
    }

    @Override
    public List<StoreDTO> getAllStores() {
        return storeRepository
                .findAll()
                .stream()
                .map(StoreMapper::toStoreDTO)
                .collect(Collectors.toList());
    }



    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDTO) throws UserException {
        User currentUser= userService.getCurrentUser();
        Store existingStore = storeRepository.findByStoreAdminId(currentUser.getId());
        if(existingStore ==null) {
            throw new UserException("INVALID ID, STORE NOT FOUND");
        }
        existingStore.setBrandName(storeDTO.getBrandName());
        existingStore.setDescription(storeDTO.getDescription());
        if(storeDTO.getStoreType()!=null) {
            existingStore.setStoreType(storeDTO.getStoreType());
        }
        if(storeDTO.getContact()!=null) {
             StoreContact storeContact= StoreContact.builder()
                    .address(storeDTO.getContact().getAddress())
                    .phone(storeDTO.getContact().getPhone())
                    .email(storeDTO.getContact().getEmail())
                    .build();
             existingStore.setContact(storeContact);
        }
        return StoreMapper.toStoreDTO(storeRepository.save(existingStore));
    }

    @Override
    public void deleteStore(Long id) throws UserException {
        Store store = getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        User admin= userService.getCurrentUser();
        Store store;
        try{
            store =storeRepository.findByStoreAdminId(admin.getId());
        }catch(Exception e) {
            throw new UserException("INVALID ID, STORE NOT FOUND");
        }

        return store;
    }

    @Override
    public StoreDTO getStoreByEmployee() throws UserException {
        User currentUser= userService.getCurrentUser();
        if(currentUser==null){
            throw new UserException("You don't have permission to access this store");
        }
        return StoreMapper.toStoreDTO(currentUser.getStore());
    }

    @Override
    public StoreDTO moderateStore(Long id, StoreStatus status) throws Exception {

        Store store = storeRepository.findById(id).orElseThrow(
                ()->new Exception("Store Not Found")
        );
        store.setStatus(status);
        return StoreMapper.toStoreDTO(storeRepository.save(store));
    }
}
