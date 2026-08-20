package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeSeriesPointDTO {
    private LocalDateTime date;
    private BigDecimal totalAmount;
}
