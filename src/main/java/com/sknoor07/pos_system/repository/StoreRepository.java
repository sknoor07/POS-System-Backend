package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.store.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByStoreAdminId(Long id);
    Long countByStatus(StoreStatus status);
    List<Store> findByStatus(StoreStatus status);

    @Query("""
        SELECT COUNT(s) from Store s WHERE DATE(s.createdAt)= :date
        """)
    Long countByDate(LocalDateTime date);

    @Query("""
         SELECT s.createdAt as regDate,COUNT(s) as count from Store s where s.createdAt>=:startDate GROUP BY s.createdAt order by regDate DESC
    """)
    List<Object[]> getStoreRegistrationStats(@Param("startDate") LocalDateTime startDate);
}
