package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data@Builder
public class StoreAlertDTO {
    private List<String> lowStockAlerts;
    private List<String> noSalesToday;
    private List<String> refundSpikesAlerts;
    private List<String> inActiveCashiers;
}
