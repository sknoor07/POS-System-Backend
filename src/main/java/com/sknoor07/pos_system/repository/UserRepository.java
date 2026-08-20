package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByStore(Store store);

    List<User> findByBranchId(Long branchId);

    @Query("""
            SELECT COUNT(u)
            FROM User u
            WHERE u.store.storeAdmin.id = :storeAdminId
            AND u.role IN (:roles)
        """)
    int countByStoreAdminIdAndRoles(@Param("storeAdminId") Long storeAdminId, @Param("roles") List<UserRole> roles);

    @Query("""
            SELECT u.fullName
            FROM User u
            WHERE u.lastLoginAt < :currentDate
            AND u.store.storeAdmin.id = :storeAdminId
            AND u.role=com.sknoor07.pos_system.modals.user.UserRole.ROLE_BRANCH_CASHIER
        """)

    List<String> findInactiveCashiers(@Param("storeAdminId") Long storeAdminId, @Param("cutOffDate")LocalDate date);
}
