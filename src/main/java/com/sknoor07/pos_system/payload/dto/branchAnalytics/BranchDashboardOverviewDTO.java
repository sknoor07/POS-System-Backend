package com.sknoor07.pos_system.payload.dto.branchAnalytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data@Builder
public class BranchDashboardOverviewDTO {
    private BigDecimal totalSales;
    private BigDecimal salesGrowth;
    private int ordersToday;
    private BigDecimal orderGrowth;
    private int activeCashiers;
    private BigDecimal cashierGrowth;
    private int lowStockItems;
    private BigDecimal lowStockGrowth;
}
