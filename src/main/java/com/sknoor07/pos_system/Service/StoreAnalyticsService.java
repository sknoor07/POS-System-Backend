package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.payload.dto.branchAnalytics.CategorySalesDTO;
import com.sknoor07.pos_system.payload.dto.storeAnalytics.*;

import java.util.List;

public interface StoreAnalyticsService {
    StoreOverviewDTO getStoreOverview(Long storeAdminId);
    TimeSeriesDataDTO getSalesTrends(Long storeAdminId, String period);
    List<TimeSeriesPointDTO> getMonthlySalesGraph(Long storeAdminId);
    List<TimeSeriesPointDTO> getDailySalesGraph(Long storeAdminId);
    //List<CategorySalesDTO> getSalesByCategory;
    List<PaymentInsightDTO> getSalesByPaymentMethod(Long storeAdminId);
    List<BranchSalesDTO> getSaleByBranch(Long storeAdminID);
    List<PaymentInsightDTO> getPaymentBreakdown(Long storeAdminId);
    BranchPerformanceDTO getBranchPerformance(Long storeAdminId);
    StoreAlertDTO getStoreAlerts(Long storeAdminId);
}
