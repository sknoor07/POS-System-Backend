package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.StoreAnalyticsService;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.storeAnalytics.*;
import com.sknoor07.pos_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreAnalyticsServiceImpl implements StoreAnalyticsService {
    private final BranchRepository branchRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RefundRepository refundRepository;
    private final ProductRepository productRepository;

    @Override
    public StoreOverviewDTO getStoreOverview(Long storeAdminId) {
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_BRANCH_MANAGER);
        roles.add(UserRole.ROLE_STORE_MANAGER);
        roles.add(UserRole.ROLE_STORE_ADMIN);
        roles.add(UserRole.ROLE_BRANCH_CASHIER);
        return StoreOverviewDTO.builder()
                .totalBranches(branchRepository.countByStoreAdminId(storeAdminId))
                .totalSales(orderRepository.sumTotalSalesByStoreAdmin(storeAdminId).orElse(BigDecimal.ZERO))
                .totalOrders(orderRepository.countByStoreAdmin(storeAdminId).orElse(0))
                .totalEmployees(userRepository.countByStoreAdminIdAndRoles(storeAdminId,roles))
                .totalCustomers(customerRepository.countByStoreAdminId(storeAdminId))
                .totalRefunds(refundRepository.countByStoreAdminId(storeAdminId))
                .totalProducts(productRepository.countByStoreAdminId(storeAdminId))
                .topBranchName(branchRepository.findTopBranchesBySales(storeAdminId))
                .build();
    }

    @Override
    public TimeSeriesDataDTO getSalesTrends(Long storeAdminId, String period) {
        return null;
    }

    @Override
    public List<TimeSeriesPointDTO> getMonthlySalesGraph(Long storeAdminId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(365);

        List<Order> orders= orderRepository.finaAllByStoreAdminAndCreatedAtBetween(storeAdminId, startDate, endDate);
        Map<YearMonth, BigDecimal> grouped = orders.stream()
                .collect(
                        Collectors.groupingBy(
                                order -> YearMonth.from(order.getCreatedAt()),
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        order -> order.getTotalAmount() != null
                                                ? order.getTotalAmount()
                                                : BigDecimal.ZERO,
                                        BigDecimal::add
                                )
                        )
                );


        return grouped.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(entry->new TimeSeriesPointDTO(entry.getKey().atDay(1).atStartOfDay(),entry.getValue())).toList();
    }

    @Override
    public List<TimeSeriesPointDTO> getDailySalesGraph(Long storeAdminId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);
        return orderRepository.getDailySales(storeAdminId, startDate, endDate);
    }

    @Override
    public List<PaymentInsightDTO> getSalesByPaymentMethod(Long storeAdminId) {

        return orderRepository.getSalesByPaymentMethod(storeAdminId);
    }

    @Override
    public List<BranchSalesDTO> getSaleByBranch(Long storeAdminID) {
        return orderRepository.getSalesByBranch(storeAdminID);
    }

    @Override
    public List<PaymentInsightDTO> getPaymentBreakdown(Long storeAdminId) {
        List<Object[]> rawData= orderRepository.getPaymentBreakdownByMethod(storeAdminId, LocalDate.now());
        return rawData.stream().map(o->{
            PaymentType paymentType= (PaymentType) o[0];
            BigDecimal totalAmount= (BigDecimal) o[1];
            return new PaymentInsightDTO(paymentType,totalAmount);
        }).toList();
    }

    @Override
    public BranchPerformanceDTO getBranchPerformance(Long storeAdminId) {
        return  BranchPerformanceDTO.builder()
                .branchSales(orderRepository.getSalesByBranch(storeAdminId))
                .newBranchesThisMonth(branchRepository.countNewBranchesThisMonth(storeAdminId))
                .topBranch(branchRepository.findTopBranchesBySales(storeAdminId))
                .build();
    }

    @Override
    public StoreAlertDTO getStoreAlerts(Long storeAdminId) {
        LocalDateTime endDate = LocalDateTime.now().minusDays(7);

        return StoreAlertDTO.builder()
                .inActiveCashiers(userRepository.findInactiveCashiers(storeAdminId,endDate.toLocalDate()))
                .lowStockAlerts(productRepository.findLowStockProduct(storeAdminId))
                .noSalesToday(branchRepository.findBranchesWithNoSales(storeAdminId))
                .refundSpikesAlerts(refundRepository.findRefundSpikes(storeAdminId))
                .build();
    }
}
