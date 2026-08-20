package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.StoreAnalyticsService;
import com.sknoor07.pos_system.payload.dto.storeAnalytics.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store-analytics")
@RequiredArgsConstructor
@Tag(name = "Store Analytics", description = "Endpoints for store-level administration analytics, multi-branch comparisons, sales trends, and system alerts")
public class StoreAnalyticsController {
    private final StoreAnalyticsService storeAnalyticsService;

    @Operation(summary = "Get Sales Trends", description = "Retrieves time-series sales trend data for a store admin filtered by period (daily, weekly, monthly, yearly).")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved sales trends")
    @GetMapping("/{storeAdminId}/sales-trends")
    public ResponseEntity<TimeSeriesDataDTO> getSalesTrends(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId,
            @Parameter(description = "Time period filter (e.g. daily, weekly, monthly, yearly)", required = true) @RequestParam String period) {
        return ResponseEntity.ok(storeAnalyticsService.getSalesTrends(storeAdminId, period));
    }

    @Operation(summary = "Get Store Overview Metrics", description = "Retrieves overall store metrics (total revenue, total orders, active branches, total customers) for a store admin.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store overview")
    @GetMapping("/{storeAdminId}/overview")
    public ResponseEntity<StoreOverviewDTO> getStoreOverview(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getStoreOverview(storeAdminId));
    }

    @Operation(summary = "Get Monthly Sales Graph", description = "Retrieves monthly sales data points for graph plotting.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved monthly sales data points")
    @GetMapping("/{storeAdminId}/monthly-sales")
    public ResponseEntity<List<TimeSeriesPointDTO>> getMonthlySales(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getMonthlySalesGraph(storeAdminId));
    }

    @Operation(summary = "Get Daily Sales Graph", description = "Retrieves daily sales data points for graph plotting.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved daily sales data points")
    @GetMapping("/{storeAdminId}/daily-sales")
    public ResponseEntity<List<TimeSeriesPointDTO>> getDailySales(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getDailySalesGraph(storeAdminId));
    }

    @Operation(summary = "Get Sales by Payment Method", description = "Retrieves store sales breakdown by payment method.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved payment insights")
    @GetMapping("/{storeAdminId}/sales-payment-method")
    public ResponseEntity<List<PaymentInsightDTO>> getSalesByPaymentMethod(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getSalesByPaymentMethod(storeAdminId));
    }

    @Operation(summary = "Get Sales by Branch", description = "Retrieves sales comparison data across all branches owned by a store admin.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch sales comparison")
    @GetMapping("/{storeAdminId}/branch-sales")
    public ResponseEntity<List<BranchSalesDTO>> getSaleByBranch(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getSaleByBranch(storeAdminId));
    }

    @Operation(summary = "Get Branch Payment Breakdown", description = "Retrieves payment method breakdown across store branches.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch payment breakdown")
    @GetMapping("/{storeAdminId}/branch-payment-breakdown")
    public ResponseEntity<List<PaymentInsightDTO>> getPaymentBreakdown(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getPaymentBreakdown(storeAdminId));
    }

    @Operation(summary = "Get Branch Performance", description = "Retrieves top performing and underperforming branches for a store admin.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch performance report")
    @GetMapping("/{storeAdminId}/branch-performance")
    public ResponseEntity<BranchPerformanceDTO> getBranchPerformance(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getBranchPerformance(storeAdminId));
    }

    @Operation(summary = "Get Store Alerts", description = "Retrieves store-level operational alerts (low stock items, refund spikes, inactive cashiers, branches with no sales).")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store alerts")
    @GetMapping("/{storeAdminId}/alert")
    public ResponseEntity<StoreAlertDTO> getStoreAlerts(
            @Parameter(description = "ID of the store admin", required = true) @PathVariable Long storeAdminId) {
        return ResponseEntity.ok(storeAnalyticsService.getStoreAlerts(storeAdminId));
    }
}
