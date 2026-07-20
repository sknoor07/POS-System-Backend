package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
