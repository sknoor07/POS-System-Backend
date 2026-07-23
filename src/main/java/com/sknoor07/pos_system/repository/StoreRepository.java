package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByStoreAdminId(Long id);
}
