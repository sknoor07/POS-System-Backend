package com.sknoor07.pos_system.modals.refund;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.modals.shiftreport.ShiftReport;
import com.sknoor07.pos_system.modals.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Order order;

    private String reason;

    private BigDecimal amount;

    @ManyToOne
    @JsonIgnore
    private ShiftReport shiftReport;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Branch branch;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
