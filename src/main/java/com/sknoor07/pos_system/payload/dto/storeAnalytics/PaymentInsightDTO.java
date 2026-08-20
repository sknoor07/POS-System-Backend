package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import com.sknoor07.pos_system.modals.orders.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInsightDTO {
    private PaymentType paymentType;
    private BigDecimal totalAmount;
}
