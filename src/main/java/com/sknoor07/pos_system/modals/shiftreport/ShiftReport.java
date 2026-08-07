package com.sknoor07.pos_system.modals.shiftreport;


import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.modals.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime shiftStartTime;

    private LocalDateTime shiftEndTime;

    private BigDecimal totalSales;

    private BigDecimal totalRefunds;

    private BigDecimal netSales;

    private int totalOrders;

    @ManyToOne
    private User cashier;
    @ManyToOne
    private Branch branch;

    @Transient
    private List<PaymentSummary> paymentSummaries;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> topSellingProducts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> recentOrders;

    @OneToMany(mappedBy = "shiftReport", cascade = CascadeType.ALL)
    private List<Refund> refunds;
}
