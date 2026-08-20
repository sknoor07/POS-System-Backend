package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.BranchAnalyticsService;
import com.sknoor07.pos_system.modals.shiftreport.PaymentSummary;
import com.sknoor07.pos_system.payload.dto.branchAnalytics.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch-analytics")
@Tag(name = "Branch Analytics", description = "Endpoints for individual branch sales charts, top products, top cashiers, category sales, and payment breakdowns")
public class BranchAnalyticsController {
    private final BranchAnalyticsService branchAnalyticsService;

    @Operation(summary = "Get Daily Sales Chart", description = "Retrieves daily sales trends for a branch over a specified number of days (default: 7 days).")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved daily sales chart data")
    @GetMapping("/daily-sales")
    public ResponseEntity<List<DailySalesDTO>> getDailySalesChart(
            @Parameter(description = "ID of the branch", required = true) @RequestParam Long branchId,
            @Parameter(description = "Number of past days to include (default 7)") @RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getDailySalesChart(branchId, days));
    }

    @Operation(summary = "Get Top Products by Quantity", description = "Retrieves the top-selling products by quantity along with percentage contribution for a specific branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved top products performance data")
    @GetMapping("/top-products")
    public ResponseEntity<List<ProductPerformanceDTO>> getTopProductsByQuantityWithPercentage(
            @Parameter(description = "ID of the branch", required = true) @RequestParam Long branchId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getTopProductsByQuantityWithPercentage(branchId));
    }

    @Operation(summary = "Get Top Cashiers Performance", description = "Retrieves cashier rankings and performance metrics based on processed order count for a branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved top cashiers performance data")
    @GetMapping("/top-cashiers")
    public ResponseEntity<List<CashierPerformanceDTO>> getTopCashierPerformanceByOrders(
            @Parameter(description = "ID of the branch", required = true) @RequestParam Long branchId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getTopCashierPerformanceByOrders(branchId));
    }

    @Operation(summary = "Get Category-wise Sales Breakdown", description = "Retrieves product category sales breakdown for a branch on a given date.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved category sales breakdown")
    @GetMapping("/category-sales")
    public ResponseEntity<List<CategorySalesDTO>> getCategoryWiseSalesBreakdown(
            @Parameter(description = "ID of the branch", required = true) @RequestParam Long branchId,
            @Parameter(description = "Target date (ISO format YYYY-MM-DD)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getCategoryWiseSalesBreakdown(branchId, date));
    }

    @Operation(summary = "Get Today's Branch Overview", description = "Retrieves today's dashboard overview metrics (total sales, order count, active shift count, etc.) for a branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved today's branch overview")
    @GetMapping("/today-overview")
    public ResponseEntity<BranchDashboardOverviewDTO> getTodayOverview(
            @Parameter(description = "ID of the branch", required = true) @RequestParam Long branchId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchAnalyticsService.getBranchOverview(branchId));
    }

    @Operation(summary = "Get Payment Method Breakdown", description = "Retrieves payment method breakdown (Cash, Card, UPI, etc.) for a branch on a specific date.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved payment method breakdown")
    @GetMapping("/payment-breakdown")
    public ResponseEntity<List<PaymentSummary>> getPaymentSummary(
            @Parameter(description = "ID of the branch", required = true) @RequestParam Long branchId,
            @Parameter(description = "Target date (ISO format YYYY-MM-DD)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(branchAnalyticsService.getPaymentMethodBreakdown(branchId, date));
    }
}
