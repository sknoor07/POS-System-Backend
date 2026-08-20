package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.RefundService;
import com.sknoor07.pos_system.mapper.RefundMapper;
import com.sknoor07.pos_system.payload.dto.RefundDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
@Tag(name = "Refund Management", description = "Endpoints for processing product/order refunds, shift refunds, and cashier refund history")
public class RefundController {
    private final RefundService refundService;

    @Operation(summary = "Create Refund", description = "Processes a new refund for an order or item with reason and cash/card reversal details.")
    @ApiResponse(responseCode = "201", description = "Refund successfully created")
    @PostMapping()
    public ResponseEntity<RefundDTO> createRefund(@RequestBody RefundDTO refundDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(refundService.createRefund(refundDTO));
    }

    @Operation(summary = "Get All Refunds", description = "Retrieves all processed refunds across the system.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved refunds list")
    @GetMapping()
    public ResponseEntity<List<RefundDTO>> getAllRefund() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getAllRefunds());
    }

    @Operation(summary = "Get Refunds by Cashier ID", description = "Retrieves all refunds processed by a specific cashier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved cashier refunds")
    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDTO>> getRefundByCashierId(
            @Parameter(description = "ID of the cashier", required = true) @PathVariable Long cashierId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByCashierId(cashierId));
    }

    @Operation(summary = "Get Refunds by Branch ID", description = "Retrieves all refunds issued at a specific branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch refunds")
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDTO>> getRefundByBranchId(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByBranchId(branchId).stream().map(RefundMapper::toDTO).toList());
    }

    @Operation(summary = "Get Refund by ID", description = "Retrieves specific refund details by refund ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved refund details")
    @ApiResponse(responseCode = "404", description = "Refund record not found")
    @GetMapping("/{id}")
    public ResponseEntity<RefundDTO> getRefundById(
            @Parameter(description = "ID of the refund", required = true) @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundById(id));
    }

    @Operation(summary = "Get Refunds by Shift Report ID", description = "Retrieves all refunds processed during a specific cashier shift report.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved shift refunds")
    @GetMapping("/shift/{shiftId}")
    public ResponseEntity<List<RefundDTO>> getRefundByShiftReportId(
            @Parameter(description = "ID of the shift report", required = true) @PathVariable Long shiftId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByShiftReport(shiftId));
    }

    @Operation(summary = "Get Refunds by Cashier and Date Range", description = "Retrieves refunds for a cashier within a specific date-time interval.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered refunds")
    @GetMapping("/cashier/{cashierId}/range")
    public ResponseEntity<List<RefundDTO>> getRefundByCashierIDAndDateRange(
            @Parameter(description = "ID of the cashier", required = true) @PathVariable Long cashierId,
            @Parameter(description = "Start date-time (ISO format)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Parameter(description = "End date-time (ISO format)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByCashierIDAndDateRange(cashierId, start, end));
    }

    @Operation(summary = "Delete Refund", description = "Deletes a refund record by ID.")
    @ApiResponse(responseCode = "200", description = "Refund record successfully deleted")
    @ApiResponse(responseCode = "404", description = "Refund record not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteRefundById(
            @Parameter(description = "ID of the refund", required = true) @PathVariable Long id) throws Exception {
        refundService.deleteRefund(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Refund Deleted successfully"));
    }
}
