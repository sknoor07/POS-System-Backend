package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.StoreAnalyticsService;
import com.sknoor07.pos_system.payload.dto.storeAnalytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store-analytics")
@RequiredArgsConstructor
public class StoreAnalyticsController {
    private final StoreAnalyticsService storeAnalyticsService;

    @GetMapping("/{storeAdminId}/sales-trends")
    public ResponseEntity<TimeSeriesDataDTO> getSalesTrends(@PathVariable Long storeAdminId, @RequestParam String period){
        return ResponseEntity.ok(storeAnalyticsService.getSalesTrends(storeAdminId, period));
    }

    @GetMapping("/{storeAdminId}/overview")
    public ResponseEntity<StoreOverviewDTO> getStoreOverview(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getStoreOverview(storeAdminId));
    }

    @GetMapping("/{storeAdminId}/monthly-sales")
    public ResponseEntity<List<TimeSeriesPointDTO>> getMonthlySales(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getMonthlySalesGraph(storeAdminId));
    }

    @GetMapping("/{storeAdminId}/daily-sales")
    public ResponseEntity<List<TimeSeriesPointDTO>> getDailySales(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getDailySalesGraph(storeAdminId));
    }

    @GetMapping("/{storeAdminId}/sales-payment-method")
    public ResponseEntity<List<PaymentInsightDTO>> getSalesByPaymentMethod(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getSalesByPaymentMethod(storeAdminId));
    }

    @GetMapping("/{storeAdminId}/branch-sales")
    public ResponseEntity<List<BranchSalesDTO>> getSaleByBranch(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getSaleByBranch(storeAdminId));
    }

    @GetMapping("/{storeAdminId}/branch-payment-breakdown")
    public ResponseEntity<List<PaymentInsightDTO>> getPaymentBreakdown(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getPaymentBreakdown(storeAdminId));
    }

    @GetMapping("/{storeAdminId}/branch-performance")
    public ResponseEntity<BranchPerformanceDTO> getBranchPerformance(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getBranchPerformance(storeAdminId));
    }

    @GetMapping("/{storeAdminId}/alert")
    public ResponseEntity<StoreAlertDTO> getStoreAlerts(@PathVariable Long storeAdminId){
        return ResponseEntity.ok(storeAnalyticsService.getStoreAlerts(storeAdminId));
    }



}
