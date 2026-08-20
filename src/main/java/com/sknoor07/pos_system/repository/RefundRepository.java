package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.modals.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
        List<Refund> findByCashierIdAndCreatedAtBetween(Long cashierId, LocalDateTime from, LocalDateTime to);
        List<Refund> findByCashierId(Long cashierId);
        List<Refund> findByShiftReportId(Long shiftReportId);
        List<Refund> findByBranchId(Long branchId);

        @Query("""
            SELECT COUNT(r)
            FROM Refund r
            WHERE r.branch.store.storeAdmin.id=:storeAdminId
        """)
        int countByStoreAdminId(@Param("storeAdminId") Long storeAdminId);

        @Query("""
            SELECT r.reason
            FROM Refund r
            WHERE r.branch.store.storeAdmin.id=:storeAdminId
            GROUP BY function('DATE',r.createdAt)
            HAVING SUM(r.amount)>5000
        """)
        List<String> findRefundSpikes(@Param("storeAdminId") Long storeAdminId);
}
