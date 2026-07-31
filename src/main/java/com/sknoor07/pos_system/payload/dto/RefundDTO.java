package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.orders.PaymentType;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RefundDTO {

    private Long id;

    private Order order;
    private Long orderId;

    private String reason;

    private BigDecimal amount;

    //private ShiftReport shiftReport;
    private Long shiftReportId;

    private UserDTO cashierDto;
    private String cashierName;

    private BranchDTO branchDto;
    private Long branchId;


    private LocalDateTime createdAt;

    private PaymentType paymentType;
}
