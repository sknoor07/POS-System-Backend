package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.shiftreport.ShiftReport;
import com.sknoor07.pos_system.payload.dto.ShiftReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {

    ShiftReportDTO startShift() throws Exception;

    ShiftReportDTO EndShift() throws Exception;

    ShiftReportDTO getShiftReportById(Long shiftReportIdx);

    List<ShiftReportDTO> getAllShiftReports();

    List<ShiftReportDTO> getAllShiftReportsByCashierId(Long cashierId);

    List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId);

    ShiftReportDTO getCurrentShiftProgress() throws Exception;

    ShiftReportDTO getShiftReportByCashierIdAndDate(Long cashierId, LocalDateTime date) throws Exception;


}
