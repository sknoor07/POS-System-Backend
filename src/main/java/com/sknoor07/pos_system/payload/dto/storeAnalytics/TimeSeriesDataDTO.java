package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data@Builder
public class TimeSeriesDataDTO {
    private List<TimeSeriesPointDTO> points;
    private  String period;
}
