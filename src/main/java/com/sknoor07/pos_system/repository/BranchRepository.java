package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.branch.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch , Long> {

    List<Branch> findByStoreId (Long storeId);

    @Query("""
            select count(b) from Branch b where b.store.storeAdmin.id=:storeAdminId
            """)
    int countByStoreAdminId(@Param("storeAdminId") Long storeAdminId);

    @Query("""
        Select count(b) from Branch b where b.store.storeAdmin.id=:storeAdminId and YEAR(b.createdAt)=YEAR(current_date) and MONTH(b.createdAt)=MONTH(current_date)
""")
    int countNewBranchesThisMonth(@Param("storeAdminId") Long storeAdminId);

    @Query("""
        SELECT b.name
            FROM Branch b
            JOIN Order o ON o.branch.id = b.id
            WHERE b.store.storeAdmin.id = :storeAdminId
            GROUP BY b.id, b.name
            ORDER BY SUM(o.totalAmount) DESC
""")
    List<String> findTopBranchesBySales(@Param("storeAdminId") Long storeAdminId);


    @Query("""
        SELECT b.name
        FROM Branch b
        WHERE b.store.storeAdmin.id = :storeAdminId
        AND b.id NOT IN (SELECT DISTINCT o.branch.id FROM Order o where DATE(o.createdAt)=current_date)
            
""")
    List<String> findBranchesWithNoSales(@Param("storeAdminId") Long storeAdminId);
}
