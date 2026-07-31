package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.RefundService;
import com.sknoor07.pos_system.mapper.RefundMapper;
import com.sknoor07.pos_system.payload.dto.RefundDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import jakarta.websocket.server.PathParam;
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
public class RefundController {
    private final RefundService refundService;

    @PostMapping()
    public ResponseEntity<RefundDTO> createRefund(@RequestBody RefundDTO refundDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(refundService.createRefund(refundDTO));
    }

    @GetMapping()
    public ResponseEntity<List<RefundDTO>> getAllRefund() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getAllRefunds());
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDTO>> getRefundByCashierId(@PathVariable Long cashierId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByCashierId(cashierId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDTO>> getRefundByBranchId(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByBranchId(branchId).stream().map(RefundMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundDTO> getRefundById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundById(id));
    }

    @GetMapping("/shift/{shiftId}")
    public ResponseEntity<List<RefundDTO>> getRefundByShiftReportId(@PathVariable Long shiftId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByShiftReport(shiftId));
    }

    @GetMapping("/cashier/{cashierId}/range")
    public ResponseEntity<List<RefundDTO>> getRefundByCashierIDAndDateRange(@PathVariable Long cashierId,
                                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefundByCashierIDAndDateRange(cashierId,start,end));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteRefundById(@PathVariable Long id) throws Exception {
        refundService.deleteRefund(id);
    return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Refund Deleted successfully"));
}
}
