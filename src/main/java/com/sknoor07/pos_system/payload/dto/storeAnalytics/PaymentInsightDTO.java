package com.sknoor07.pos_system.payload.dto.storeAnalytics;

import com.sknoor07.pos_system.modals.orders.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentInsightDTO {
    private PaymentType paymentType;
    private BigDecimal totalAmount;
}
