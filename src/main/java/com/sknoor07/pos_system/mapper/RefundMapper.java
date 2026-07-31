package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.payload.dto.RefundDTO;

public class RefundMapper {

    public static RefundDTO toDTO(Refund refund){
        return RefundDTO.builder()
                .id(refund.getId())
                .orderId(refund.getId())
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .shiftReportId(refund.getShiftReport()!=null?refund.getShiftReport().getId():null)
                .cashierName(refund.getCashier().getFullName())
                .branchId(refund.getBranch().getId())
                .paymentType(refund.getPaymentType())
                .createdAt(refund.getCreatedAt())
                .build();
    }


}
