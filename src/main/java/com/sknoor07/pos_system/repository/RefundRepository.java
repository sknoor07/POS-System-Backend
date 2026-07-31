package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.modals.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
        List<Refund> findByCashierIdAndCreatedAtBetween(Long cashierId, LocalDateTime from, LocalDateTime to);
        List<Refund> findByCashierId(Long cashierId);
        List<Refund> findByShiftReportId(Long shiftReportId);
        List<Refund> findByBranchId(Long branchId);

}
