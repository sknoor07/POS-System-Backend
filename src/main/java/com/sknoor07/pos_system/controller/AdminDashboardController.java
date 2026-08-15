package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.AdminDashboardService;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.DashboardSummaryDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreRegistrationStateDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreStatusDistributionDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final AdminDashboardService adminDashboardService;

    @GetMapping("/dashboard/summary")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(adminDashboardService.getDashboardSummary());
    }

    @GetMapping("/dashboard/store-registration")
    public ResponseEntity<List<StoreRegistrationStateDTO>> getLast7DaysRegistrationStats() {
        return ResponseEntity.status(HttpStatus.OK).body(adminDashboardService.getLst7DayRegistrationStats());
    }

    @GetMapping("/dashboard/store-status-distribution")
    public ResponseEntity<StoreStatusDistributionDTO> getStoreStatusDistribution() {
        return ResponseEntity.status(HttpStatus.OK).body(adminDashboardService.getStoreStatusDistribution());
    }
}
