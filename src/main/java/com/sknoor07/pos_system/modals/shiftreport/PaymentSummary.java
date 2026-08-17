package com.sknoor07.pos_system.modals.shiftreport;

import com.sknoor07.pos_system.modals.orders.PaymentType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSummary {

    private PaymentType paymentType;

    private BigDecimal totalAmount;

    private int transactionCount;

    private BigDecimal percentage;

}
