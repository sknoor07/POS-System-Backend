package com.sknoor07.pos_system.payload.dto.branchAnalytics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductPerformanceDTO {
    private String productName;
    private Long quantitySold;
    private double percentage;
}
