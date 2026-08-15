package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.payload.dto.adminAnalytics.DashboardSummaryDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreRegistrationStateDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreStatusDistributionDTO;

import java.util.List;

public interface AdminDashboardService {
    DashboardSummaryDTO getDashboardSummary();
    List<StoreRegistrationStateDTO> getLst7DayRegistrationStats();
    StoreStatusDistributionDTO getStoreStatusDistribution();

}
