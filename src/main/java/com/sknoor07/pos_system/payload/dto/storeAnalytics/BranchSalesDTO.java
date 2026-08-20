package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data@Builder
public class BranchSalesDTO {
    private String branchName;
    private BigDecimal totalSales;
}
