package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.BranchAnalyticsService;
import com.sknoor07.pos_system.modals.shiftreport.PaymentSummary;
import com.sknoor07.pos_system.payload.dto.branchAnalytics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch-analytics")
public class BranchAnalyticsController {
    private final BranchAnalyticsService branchAnalyticsService;

    @GetMapping("/daily-sales")
    public ResponseEntity<List<DailySalesDTO>> getDailySalesChart(@RequestParam Long branchId, @RequestParam (defaultValue = "7") int days) {
        return  ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getDailySalesChart(branchId,days));
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<ProductPerformanceDTO>> getTopProductsByQuantityWithPercentage(@RequestParam Long branchId) {
        return  ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getTopProductsByQuantityWithPercentage(branchId));
    }

    @GetMapping("/top-cashiers")
    public ResponseEntity<List<CashierPerformanceDTO>> getTopCashierPerformanceByOrders(@RequestParam Long branchId) {
        return  ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getTopCashierPerformanceByOrders(branchId));
    }

    @GetMapping("/category-sales")
    public ResponseEntity<List<CategorySalesDTO>> getCategoryWiseSalesBreakdown(@RequestParam Long branchId, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        return  ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getCategoryWiseSalesBreakdown(branchId,date));
    }

    @GetMapping("/today-overview")
    public ResponseEntity<BranchDashboardOverviewDTO> getTodayOverview(@RequestParam Long branchId) {
        return  ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getBranchOverview(branchId));
    }

    @GetMapping("/payment-breakdown")
    public ResponseEntity<List<PaymentSummary>> getPaymentSummary(
            @RequestParam Long branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(
                branchAnalyticsService.getPaymentMethodBreakdown(branchId, date)
        );
    }

}
