package com.sknoor07.pos_system;

import com.sknoor07.pos_system.Service.Impl.BranchAnalyticsServiceImpl;
import com.sknoor07.pos_system.payload.dto.branchAnalytics.BranchDashboardOverviewDTO;
import com.sknoor07.pos_system.repository.InventoryRepository;
import com.sknoor07.pos_system.repository.OrderItemsRepository;
import com.sknoor07.pos_system.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BranchAnalyticsServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private BranchAnalyticsServiceImpl branchAnalyticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void getBranchOverview_LowStockAnalytics_DoesNotFabricateYesterdayOrReportGrowth() {
        Long branchId = 1L;

        when(orderRepository.getTotalSalesBetween(eq(branchId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(BigDecimal.valueOf(1000)));
        when(orderRepository.countOrdersByBranchAndDate(eq(branchId), any(LocalDateTime.class))).thenReturn(10);
        when(orderRepository.countDistinctCashierByBranchAndDate(eq(branchId), any(LocalDateTime.class))).thenReturn(2);
        when(inventoryRepository.countLowStockItems(branchId)).thenReturn(5);

        BranchDashboardOverviewDTO overview = branchAnalyticsService.getBranchOverview(branchId);

        assertNotNull(overview);
        assertEquals(5, overview.getLowStockItems());
        assertNull(overview.getLowStockGrowth());
        verify(inventoryRepository, times(1)).countLowStockItems(branchId);
    }*/
}
