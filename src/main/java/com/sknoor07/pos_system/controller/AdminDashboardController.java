package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.AdminDashboardService;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.DashboardSummaryDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreRegistrationStateDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreStatusDistributionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
@Tag(name = "Super Admin Dashboard", description = "Endpoints for platform super-admin metrics, store registration trends, and store status distribution")
public class AdminDashboardController {
    private final AdminDashboardService adminDashboardService;

    @Operation(summary = "Get Super Admin Dashboard Summary", description = "Retrieves high-level summary metrics for the super admin dashboard including total stores, total revenue, active users, and system health.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved super admin dashboard summary")
    @GetMapping("/dashboard/summary")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(adminDashboardService.getDashboardSummary());
    }

    @Operation(summary = "Get Store Registration Statistics (Last 7 Days)", description = "Retrieves store registration stats grouped by date over the past 7 days.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store registration stats")
    @GetMapping("/dashboard/store-registration")
    public ResponseEntity<List<StoreRegistrationStateDTO>> getLast7DaysRegistrationStats() {
        return ResponseEntity.status(HttpStatus.OK).body(adminDashboardService.getLst7DayRegistrationStats());
    }

    @Operation(summary = "Get Store Status Distribution", description = "Retrieves the count and percentage breakdown of stores by operational status (Active, Inactive, Suspended).")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store status distribution")
    @GetMapping("/dashboard/store-status-distribution")
    public ResponseEntity<StoreStatusDistributionDTO> getStoreStatusDistribution() {
        return ResponseEntity.status(HttpStatus.OK).body(adminDashboardService.getStoreStatusDistribution());
    }
}
