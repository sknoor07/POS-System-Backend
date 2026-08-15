package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.AdminDashboardService;
import com.sknoor07.pos_system.modals.store.StoreStatus;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.DashboardSummaryDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreRegistrationStateDTO;
import com.sknoor07.pos_system.payload.dto.adminAnalytics.StoreStatusDistributionDTO;
import com.sknoor07.pos_system.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final StoreRepository storeRepository;

    @Override
    public DashboardSummaryDTO getDashboardSummary() {
        Long total= storeRepository.count();
        Long active= storeRepository.countByStatus(StoreStatus.ACTIVE);
        Long pending= storeRepository.countByStatus(StoreStatus.PENDING);
        Long blocked= storeRepository.countByStatus(StoreStatus.BLOCKED);

        return DashboardSummaryDTO.builder()
                .totalStore(total)
                .activeStore(active)
                .pendingStore(pending)
                .blockedStore(blocked)
                .build();
    }

    @Override
    public List<StoreRegistrationStateDTO> getLst7DayRegistrationStats() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = today.minusDays(6);
        List<Object[]> rawStats= storeRepository.getStoreRegistrationStats(sevenDaysAgo);
        return rawStatsToStoreRegistrationDTO(rawStats,sevenDaysAgo);
    }

    @Override
    public StoreStatusDistributionDTO getStoreStatusDistribution() {
        Long active= storeRepository.countByStatus(StoreStatus.ACTIVE);
        Long pending= storeRepository.countByStatus(StoreStatus.PENDING);
        Long blocked= storeRepository.countByStatus(StoreStatus.BLOCKED);

        return StoreStatusDistributionDTO.builder()
                .active(active)
                .blocked(blocked)
                .pending(pending)
                .build();
    }

    private List<StoreRegistrationStateDTO> rawStatsToStoreRegistrationDTO(List<Object[]> rawStats,LocalDateTime sevenDaysAgo){
        Map<String, Long> dataMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for( int i=0;i<7;i++){
            LocalDateTime date = sevenDaysAgo.plusDays(i);
            dataMap.put(date.format(formatter),0L);
        }

        for(Object[] row:rawStats ){
            LocalDateTime date= (LocalDateTime) row[0];
            Long count= (Long) row[1];
            dataMap.put(date.format(formatter),count);
        }
        List<StoreRegistrationStateDTO> stats= new ArrayList<>();
        dataMap.forEach((date,count)->{
            stats.add(
                    StoreRegistrationStateDTO.builder().date(date).count(count).build()
            );
        });
        return stats;
    }
}
