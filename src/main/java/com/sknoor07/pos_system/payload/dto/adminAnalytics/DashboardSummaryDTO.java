package com.sknoor07.pos_system.payload.dto.adminAnalytics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryDTO {
    private Long totalStore;
    private Long activeStore;
    private Long blockedStore;
    private Long pendingStore;

}
