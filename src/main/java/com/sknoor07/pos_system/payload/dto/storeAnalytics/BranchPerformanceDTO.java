package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data@Builder
public class BranchPerformanceDTO {
    private List<BranchSalesDTO> branchSales;
    private Integer newBranchesThisMonth;
    private List<String> topBranch;

}
