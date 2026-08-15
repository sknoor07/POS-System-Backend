package com.sknoor07.pos_system.payload.dto.branchAnalytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class DailySalesDTO {
    private LocalDateTime date;
    private BigDecimal totalSales;

}
