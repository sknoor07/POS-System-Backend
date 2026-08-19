package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.shiftreport.PaymentSummary;
import com.sknoor07.pos_system.payload.dto.branchAnalytics.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BranchAnalyticsService {
    List<DailySalesDTO> getDailySalesChart(Long branchId, int days);

    List<ProductPerformanceDTO> getTopProductsByQuantityWithPercentage(Long branchId);

    List<CashierPerformanceDTO> getTopCashierPerformanceByOrders(Long branchId);

    List<CategorySalesDTO> getCategoryWiseSalesBreakdown(Long branchId, LocalDate dateTime);

    BranchDashboardOverviewDTO getBranchOverview(Long branchId);

    List<PaymentSummary> getPaymentMethodBreakdown(Long branchId,LocalDate dateTime);
}
