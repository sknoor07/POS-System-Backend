package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.branch.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch , Long> {

    List<Branch> findByStoreId (Long storeId);

}
