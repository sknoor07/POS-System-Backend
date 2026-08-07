package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.shiftreport.ShiftReport;
import com.sknoor07.pos_system.modals.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ShiftReportRepository extends JpaRepository<ShiftReport, Long> {
    List<ShiftReport> findByCashierId(Long id);
    List<ShiftReport> findByBranchId(Long id);

    Optional<ShiftReport> findTopByCashierAndShiftEndTimeIsNullOrderByShiftStartTimeDesc(User cashier);

    Optional<ShiftReport> findByCashierAndShiftStartTimeBetween(User cashier, LocalDateTime startDate, LocalDateTime endDate);


}
