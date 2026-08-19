package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.BranchAnalyticsService;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.modals.shiftreport.PaymentSummary;
import com.sknoor07.pos_system.payload.dto.branchAnalytics.*;
import com.sknoor07.pos_system.repository.InventoryRepository;
import com.sknoor07.pos_system.repository.OrderItemsRepository;
import com.sknoor07.pos_system.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchAnalyticsServiceImpl implements BranchAnalyticsService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final InventoryRepository inventoryRepository;


    @Override
    public List<DailySalesDTO> getDailySalesChart(Long branchId, int days) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.minusDays(days-1);
        List<DailySalesDTO> salesChart= new ArrayList<>();
        for(int i=0; i<days; i++) {
            LocalDateTime currentDate = startDate.plusDays(i);
            LocalDateTime start= currentDate.toLocalDate().atStartOfDay();
            LocalDateTime end = currentDate.toLocalDate().atTime(LocalTime.MAX);

            BigDecimal total= orderRepository.getTotalSalesBetween(branchId,start,end).orElse(BigDecimal.ZERO);
            salesChart.add(DailySalesDTO.builder()
                            .date(currentDate)
                            .totalSales(total)
                            .build());

        }
        return salesChart;
    }

    @Override
    public List<ProductPerformanceDTO> getTopProductsByQuantityWithPercentage(Long branchId) {
        List<Object[]> rawData= orderItemsRepository.getTopProductByQuantity(branchId);
        long totalQuantity= rawData.stream().mapToLong(obj->(Long)obj[2]).sum();
        return rawData.stream().limit(5).map(obj->{
                String name = (String) obj[1];
                Long quantity = (Long) obj[2];
                double percentage=totalQuantity==0?0: new BigDecimal(quantity).divide(new BigDecimal(totalQuantity), 2, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100)).doubleValue();
                return ProductPerformanceDTO.builder().productName(name).quantitySold(quantity).percentage(percentage).build();
            }).toList();
    }

    @Override
    public List<CashierPerformanceDTO> getTopCashierPerformanceByOrders(Long branchId) {
        List<Object[]> rawData= orderRepository.getTopCashierByRevenue(branchId);
        return rawData.stream().limit(5).map(obj->{
            Long cashierId = (Long) obj[0];
            String name = (String) obj[1];
            BigDecimal totalAmount= (BigDecimal) obj[2];
            Long totalOrders = (Long) obj[3];
            return CashierPerformanceDTO.builder().cashierId(cashierId).cashierName(name).totalRevenue(totalAmount).totalOrders(totalOrders).build();
        }).toList();
    }

    @Override
    public List<CategorySalesDTO> getCategoryWiseSalesBreakdown(Long branchId, LocalDate dateTime) {
        LocalDateTime startDate = dateTime.atStartOfDay();
        LocalDateTime endDate = dateTime.atTime(LocalTime.MAX);

        List<Object[]> rawData= orderItemsRepository.getCategoryWiseSales(branchId,startDate,endDate);


        return rawData.stream().map(obj->{
            return CategorySalesDTO.builder().categoryName((String) obj[0]).totalSales((BigDecimal) obj[1]).quantitySold((Long) obj[2]).build();
        }).toList();

    }

    @Override
    public List<PaymentSummary> getPaymentMethodBreakdown(Long branchId, LocalDate dateTime) {
        List<Object[]> rawData=orderRepository.getPaymentBreakdownByMethod(branchId,dateTime);

        BigDecimal total= rawData.stream().map(obj->new BigDecimal(obj[1].toString())).reduce(BigDecimal.ZERO,BigDecimal::add);

        return rawData.stream().map(obj->{
            PaymentType paymentType = (PaymentType) obj[0];
            BigDecimal amount= new BigDecimal(obj[1].toString());
            int count = ((Long) obj[2]).intValue();
            BigDecimal percentage= total.compareTo(BigDecimal.ZERO)==0 ?BigDecimal.ZERO:amount.multiply(BigDecimal.valueOf(100))
                    .divide(total, 2, RoundingMode.HALF_DOWN);
            return new PaymentSummary(paymentType,amount,count,percentage);
        }).toList();

    }

    @Override
    public BranchDashboardOverviewDTO getBranchOverview(Long branchId) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        BigDecimal todaySales= orderRepository.getTotalSalesBetween(branchId,today.atStartOfDay(),today.atTime(LocalTime.MAX)).orElse(BigDecimal.ZERO);
        BigDecimal yesterdaySales= orderRepository.getTotalSalesBetween(branchId,yesterday.atStartOfDay(),yesterday.atTime(LocalTime.MAX)).orElse(BigDecimal.ZERO);
        BigDecimal salesGrowth= calculateGrowth(todaySales,yesterdaySales);

        int todayOrders= orderRepository.countOrdersByBranchAndDate(branchId,today);
        int yesterdayOrders=orderRepository.countOrdersByBranchAndDate(branchId,yesterday);

        BigDecimal orderGrowth= calculateGrowth(new BigDecimal(todayOrders),new BigDecimal(yesterdayOrders));

        //active cashier
        int todayCashier= orderRepository.countDistinctCashierByBranchAndDate(branchId,today);
        int yesterdayCashier= orderRepository.countDistinctCashierByBranchAndDate(branchId,yesterday);

        BigDecimal cashierGrowth= calculateGrowth(new BigDecimal(todayCashier),new BigDecimal(yesterdayCashier));

        //low stock
        int todayLowStocks= inventoryRepository.countLowStockItems(branchId);


        return BranchDashboardOverviewDTO.builder()
                .totalSales(todaySales)
                .salesGrowth(salesGrowth)
                .ordersToday(todayOrders)
                .orderGrowth(orderGrowth)
                .activeCashiers(todayCashier)
                .cashierGrowth(cashierGrowth)
                .lowStockItems(todayLowStocks)
                .build();

    }

    private BigDecimal calculateGrowth(BigDecimal today, BigDecimal yesterday) {
        if( yesterday==null|| yesterday.compareTo(BigDecimal.ZERO)==0) {
            return BigDecimal.ZERO;
        }

        return  today
                .subtract(yesterday)
                .divide(yesterday, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
