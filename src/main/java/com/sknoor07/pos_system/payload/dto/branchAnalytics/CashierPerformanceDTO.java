package com.sknoor07.pos_system.payload.dto.branchAnalytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data@Builder
public class CashierPerformanceDTO {
    private Long cashierId;
    private String cashierName;
    private Long totalOrders;
    private BigDecimal totalRevenue;
}
