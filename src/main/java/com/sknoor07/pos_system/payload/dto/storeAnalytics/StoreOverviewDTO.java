package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data@Builder
public class StoreOverviewDTO {
    private Integer totalBranches;
    private BigDecimal totalSales;
    private Integer totalOrders;
    private Integer totalEmployees;
    private Integer totalCustomers;
    private Integer totalRefunds;
    private Integer totalProducts;
    private List<String> topBranchName;
}
