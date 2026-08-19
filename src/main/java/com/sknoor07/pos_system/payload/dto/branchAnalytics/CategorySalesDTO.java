package com.sknoor07.pos_system.payload.dto.branchAnalytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data@Builder
public class CategorySalesDTO {
    private String categoryName;
    private Long totalSales;
    private Long quantitySold;

}
