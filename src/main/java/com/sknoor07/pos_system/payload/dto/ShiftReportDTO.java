package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.modals.shiftreport.PaymentSummary;
import com.sknoor07.pos_system.modals.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ShiftReportDTO {

    private long id;

    private LocalDateTime shiftStartTime;

    private LocalDateTime shiftEndTime;

    private BigDecimal totalSales;

    private BigDecimal totalRefunds;

    private BigDecimal netSales;

    private int totalOrders;

    private Long cashierId;
    private UserDTO cashierDto;

    private Long branchId;
    private BranchDTO branchDto;


    private List<PaymentSummary> paymentSummaries;


    private List<ProductDTO> topSellingProducts;


    private List<OrderDTO> recentOrders;


    private List<RefundDTO> refunds;
}
