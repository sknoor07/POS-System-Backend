package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.payload.dto.RefundDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {
    RefundDTO createRefund(RefundDTO refundDTO) throws Exception;

    List<RefundDTO> getAllRefunds() throws Exception;

    List<RefundDTO> getRefundByCashierId(Long cashierId) throws Exception;

    List<RefundDTO> getRefundByShiftReport(Long shiftReportId) throws Exception;

    List<RefundDTO> getRefundByCashierIDAndDateRange(Long cashierId, LocalDateTime Start, LocalDateTime end) throws Exception;

    List<Refund> getRefundByBranchId(Long branchId) throws Exception;

    RefundDTO getRefundById(Long refundId) throws Exception;

    void deleteRefund(Long refundId) throws Exception;
}