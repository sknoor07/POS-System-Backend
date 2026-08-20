package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.ShiftReportService;
import com.sknoor07.pos_system.payload.dto.ShiftReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shift-reports")
@Tag(name = "Shift Report Management", description = "Endpoints for starting/ending cashier shifts, shift progress tracking, and drawer balancing reports")
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    @Operation(summary = "Start Shift", description = "Starts a new shift for the authenticated cashier, opening the cash drawer and logging start time.")
    @ApiResponse(responseCode = "200", description = "Shift successfully started")
    @PostMapping("/start")
    public ResponseEntity<ShiftReportDTO> startShift() throws Exception {
        return ResponseEntity.ok(shiftReportService.startShift());
    }

    @Operation(summary = "End Shift", description = "Closes the current active cashier shift, calculating cash drawer total, total sales, and discrepancies.")
    @ApiResponse(responseCode = "200", description = "Shift successfully ended")
    @PatchMapping("/end")
    public ResponseEntity<ShiftReportDTO> endShift() throws Exception {
        return ResponseEntity.ok(shiftReportService.EndShift());
    }

    @Operation(summary = "Get Shift Report by ID", description = "Retrieves detailed shift report details by shift report ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved shift report")
    @ApiResponse(responseCode = "404", description = "Shift report not found")
    @GetMapping("/{shiftReportId}")
    public ResponseEntity<ShiftReportDTO> getShiftReportById(
            @Parameter(description = "ID of the shift report", required = true) @PathVariable Long shiftReportId) {
        return ResponseEntity.ok(shiftReportService.getShiftReportById(shiftReportId));
    }

    @Operation(summary = "Get All Shift Reports", description = "Retrieves all shift reports recorded in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved shift reports list")
    @GetMapping
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReports() {
        return ResponseEntity.ok(shiftReportService.getAllShiftReports());
    }

    @Operation(summary = "Get Shift Reports by Cashier ID", description = "Retrieves all past and current shift reports for a specific cashier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved cashier shift reports")
    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReportsByCashierId(
            @Parameter(description = "ID of the cashier", required = true) @PathVariable Long cashierId) {
        return ResponseEntity.ok(shiftReportService.getAllShiftReportsByCashierId(cashierId));
    }

    @Operation(summary = "Get Shift Reports by Branch ID", description = "Retrieves all shift reports associated with a specific branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch shift reports")
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDTO>> getShiftReportsByBranchId(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId) {
        return ResponseEntity.ok(shiftReportService.getShiftReportsByBranchId(branchId));
    }

    @Operation(summary = "Get Current Active Shift Progress", description = "Retrieves real-time progress, current sales, and duration of the authenticated cashier's ongoing shift.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved current shift progress")
    @GetMapping("/current")
    public ResponseEntity<ShiftReportDTO> getCurrentShiftProgress() throws Exception {
        return ResponseEntity.ok(shiftReportService.getCurrentShiftProgress());
    }

    @Operation(summary = "Get Shift Report by Cashier and Date", description = "Retrieves a cashier's shift report for a specific date and time.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching shift report")
    @GetMapping("/cashier/{cashierId}/date")
    public ResponseEntity<ShiftReportDTO> getShiftReportByCashierIdAndDate(
            @Parameter(description = "ID of the cashier", required = true) @PathVariable Long cashierId,
            @Parameter(description = "Date and time (ISO format)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) throws Exception {
        return ResponseEntity.ok(shiftReportService.getShiftReportByCashierIdAndDate(cashierId, date));
    }
}
