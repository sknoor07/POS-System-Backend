package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.ShiftReportService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.mapper.ShiftReportMapper;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.orders.OrderItem;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.modals.shiftreport.PaymentSummary;
import com.sknoor07.pos_system.modals.shiftreport.ShiftReport;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.ShiftReportDTO;
import com.sknoor07.pos_system.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserService userService;
    private final BranchRepository branchRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ShiftReportDTO startShift() throws Exception {
        User currentUser = userService.getCurrentUser();
        LocalDateTime shiftStartTime=LocalDateTime.now();
        LocalDateTime startOfDay= shiftStartTime.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStartTime.withHour(23).withMinute(59).withSecond(59);
        Optional<ShiftReport> existingShift = shiftReportRepository.findByCashierAndShiftStartTimeBetween(currentUser, startOfDay, endOfDay);
        if(existingShift.isPresent()) {
            throw new Exception("Shift Already Exists");
        }
        Branch  branch =currentUser.getBranch();
        ShiftReport shiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .branch(branch)
                .shiftStartTime(shiftStartTime)
                .build();
        return ShiftReportMapper.toDto(shiftReportRepository.save(shiftReport));
    }

    @Override
    public ShiftReportDTO EndShift() throws Exception {
        User currentUser = userService.getCurrentUser();
        ShiftReport shiftReport= shiftReportRepository.findTopByCashierAndShiftEndTimeIsNullOrderByShiftStartTimeDesc(currentUser).orElseThrow(()-> new Exception("Shift Not Found"));
        shiftReport.setShiftEndTime(LocalDateTime.now());
        List<Refund> refunds= refundRepository.findByCashierIdAndCreatedAtBetween(currentUser.getId(),shiftReport.getShiftStartTime(),shiftReport.getShiftEndTime());
        List<Order> orders= orderRepository.findByCashierAndCreatedAtBetween(currentUser,shiftReport.getShiftStartTime(),shiftReport.getShiftEndTime());
        BigDecimal totalRefunds=refunds.stream().map(r->r.getAmount()!=null?r.getAmount():new BigDecimal(0)).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSales= orders.stream().map(Order::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        int totalOrders= orders.size();
        BigDecimal netSales=totalSales.subtract(totalRefunds);
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSales(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        shiftReport.setRefunds(refunds);

        return ShiftReportMapper.toDto(shiftReportRepository.save(shiftReport));
    }


    @Override
    public ShiftReportDTO getShiftReportById(Long shiftReportId) {
        return ShiftReportMapper.toDto(shiftReportRepository.findById(shiftReportId).orElseThrow(()-> new EntityNotFoundException("Shift Report Not Found"+shiftReportId)));
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReports() {
        return shiftReportRepository.findAll().stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReportsByCashierId(Long cashierId) {
        return shiftReportRepository.findByCashierId(cashierId).stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId) {
        return shiftReportRepository.findByBranchId(branchId).stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ShiftReportDTO getCurrentShiftProgress() throws Exception {
        User currentUser = userService.getCurrentUser();
        ShiftReport currentShift= shiftReportRepository.findTopByCashierAndShiftEndTimeIsNullOrderByShiftStartTimeDesc(currentUser).orElseThrow(()-> new Exception("Shift Not Found"));
        LocalDateTime now = LocalDateTime.now();
        List<Order> orders= orderRepository.findByCashierAndCreatedAtBetween(currentUser,currentShift.getShiftStartTime(),now);
        List<Refund> refunds= refundRepository.findByCashierIdAndCreatedAtBetween(currentUser.getId(),currentShift.getShiftStartTime(),now);
        BigDecimal totalRefunds=refunds.stream().map(r->r.getAmount()!=null?r.getAmount():new BigDecimal(0)).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSales= orders.stream().map(Order::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        int totalOrders= orders.size();
        BigDecimal netSales=totalSales.subtract(totalRefunds);
        currentShift.setTotalRefunds(totalRefunds);
        currentShift.setTotalSales(totalSales);
        currentShift.setTotalOrders(totalOrders);
        currentShift.setNetSales(netSales);
        currentShift.setRecentOrders(getRecentOrders(orders));
        currentShift.setTopSellingProducts(getTopSellingProducts(orders));
        currentShift.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        currentShift.setRefunds(refunds);
        return ShiftReportMapper.toDto(currentShift);
    }

    @Override
    public ShiftReportDTO getShiftReportByCashierIdAndDate(Long cashierId, LocalDateTime date) throws Exception {
        User cashierUser = userRepository.findById(cashierId).orElseThrow(()-> new EntityNotFoundException("Cashier Not Found"));
        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);
        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartTimeBetween(cashierUser,start,end).orElseThrow(()-> new Exception("Shift Report Not Found with Given Cashier Id: "+cashierId));
        return ShiftReportMapper.toDto(report);
    }


    //-------------------------------------Helper Methods-------------------------------------------------------------------------//


    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, BigDecimal totalSales) {
        Map<PaymentType, List<Order>> groupOrderByPaymentType = orders.stream()
                .collect(Collectors.groupingBy(o->o.getPaymentType()!=null?o.getPaymentType():PaymentType.CASH));
        List<PaymentSummary> summaries= new ArrayList<>();
        for(Map.Entry<PaymentType, List<Order>> entry: groupOrderByPaymentType.entrySet()) {
            PaymentType paymentType = entry.getKey();
            List<Order> orderList = entry.getValue();

            BigDecimal amount= orderList.stream().map(Order::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            int transactions= orderList.size();
            BigDecimal percentage = amount
                    .multiply(BigDecimal.valueOf(100))
                    .divide(totalSales, 2, RoundingMode.HALF_EVEN);
            PaymentSummary ps= new PaymentSummary();
            ps.setPaymentType(paymentType);
            ps.setTotalAmount(amount);
            ps.setTransactionCount(transactions);
            ps.setPercentage(percentage);
            summaries.add(ps);
        }
        return summaries;
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product,Integer> productSalesMap = new HashMap<>();

        for(Order order:orders) {
            for(OrderItem orderItem:order.getOrderItems()) {
                Product product = orderItem.getProduct();
                productSalesMap.put(product,productSalesMap.getOrDefault(product,0)+1);
            }
        }

        return productSalesMap
                .entrySet()
                .stream()
                .sorted((a,b)->b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::getCreatedAt).reversed()).limit(5).collect(Collectors.toList());
    }

}
