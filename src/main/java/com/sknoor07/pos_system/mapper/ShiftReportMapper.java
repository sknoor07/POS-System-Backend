package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.shiftreport.ShiftReport;
import com.sknoor07.pos_system.payload.dto.ShiftReportDTO;
import com.sknoor07.pos_system.payload.dto.UserDTO;

public class ShiftReportMapper {
    public static ShiftReportDTO toDto(ShiftReport shiftReport) {
        return ShiftReportDTO.builder()
                .id(shiftReport.getId())
                .shiftStartTime(shiftReport.getShiftStartTime())
                .shiftEndTime(shiftReport.getShiftEndTime())
                .totalSales(shiftReport.getTotalSales())
                .totalRefunds(shiftReport.getTotalRefunds())
                .netSales(shiftReport.getNetSales())
                .totalOrders(shiftReport.getTotalOrders())
                .cashierDto(UserMapper.toDTO(shiftReport.getCashier()))
                .branchDto(BranchMapper.toBranchDto(shiftReport.getBranch()))
                .cashierId(shiftReport.getCashier().getId())
                .branchId(shiftReport.getBranch().getId())
                .paymentSummaries(shiftReport.getPaymentSummaries())
                .topSellingProducts((shiftReport.getTopSellingProducts()==null|| shiftReport.getTopSellingProducts().isEmpty())?null:shiftReport.getTopSellingProducts().stream().map(ProductMapper::toProductDTO).toList())
                .recentOrders((shiftReport.getRecentOrders()==null || shiftReport.getRecentOrders().isEmpty())?null:shiftReport.getRecentOrders().stream().map(OrderMapper::toDTO).toList())
                .refunds((shiftReport.getRefunds()==null|| shiftReport.getRefunds().isEmpty())?null:shiftReport.getRefunds().stream().map(RefundMapper::toDTO).toList())
                .build();
    }




}
