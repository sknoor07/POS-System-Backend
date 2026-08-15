package com.sknoor07.pos_system.payload.dto.adminAnalytics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreRegistrationStateDTO {
    private String date;
    private Long count;
}
