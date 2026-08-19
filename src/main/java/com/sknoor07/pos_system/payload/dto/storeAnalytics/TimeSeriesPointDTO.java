package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data@Builder
public class TimeSeriesPointDTO {
    private LocalDateTime date;
    private BigDecimal totalAmount;
}
