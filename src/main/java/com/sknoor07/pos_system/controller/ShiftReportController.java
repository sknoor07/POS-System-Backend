package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.ShiftReportService;
import com.sknoor07.pos_system.payload.dto.ShiftReportDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shift-reports")
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    @PostMapping("/start")
    public ResponseEntity<ShiftReportDTO> startShift() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.startShift()
        );
    }

    @PatchMapping("/end")
    public ResponseEntity<ShiftReportDTO> endShift() throws Exception {

        return ResponseEntity.ok(
                shiftReportService.EndShift()
        );
    }

    @GetMapping("/{shiftReportId}")
    public ResponseEntity<ShiftReportDTO> getShiftReportById(@PathVariable Long shiftReportId) {
        return ResponseEntity.ok(shiftReportService.getShiftReportById(shiftReportId));
    }

    @GetMapping
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReports() {
        return ResponseEntity.ok(shiftReportService.getAllShiftReports());
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReportsByCashierId(@PathVariable Long cashierId) {
        return ResponseEntity.ok(shiftReportService.getAllShiftReportsByCashierId(cashierId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDTO>> getShiftReportsByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(shiftReportService.getShiftReportsByBranchId(branchId));
    }

    @GetMapping("/current")
    public ResponseEntity<ShiftReportDTO> getCurrentShiftProgress() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.getCurrentShiftProgress()
        );
    }

    @GetMapping("/cashier/{cashierId}/date")
    public ResponseEntity<ShiftReportDTO> getShiftReportByCashierIdAndDate(@PathVariable Long cashierId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) throws Exception {
        return ResponseEntity.ok(shiftReportService.getShiftReportByCashierIdAndDate(cashierId, date));
    }

}
