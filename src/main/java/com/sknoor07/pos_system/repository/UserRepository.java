package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByStore(Store store);

    List<User> findByBranchId(Long branchId);
}
