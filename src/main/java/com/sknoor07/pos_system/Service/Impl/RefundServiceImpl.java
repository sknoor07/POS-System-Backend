package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.OrderService;
import com.sknoor07.pos_system.Service.RefundService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.OrderNotFoundException;
import com.sknoor07.pos_system.exceptions.RefundNotFound;
import com.sknoor07.pos_system.mapper.RefundMapper;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.refund.Refund;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.RefundDTO;
import com.sknoor07.pos_system.repository.OrderRepository;
import com.sknoor07.pos_system.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final RefundRepository refundRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    public RefundDTO createRefund(RefundDTO refundDTO) throws Exception {
        User cashier= userService.getCurrentUser();
        Order order=orderRepository.findById(refundDTO.getOrderId()).orElseThrow(()-> new OrderNotFoundException("Order is Missing for this refund in order Repo"));
        Branch branch= order.getBranch();
        Refund refund=Refund.builder()
                .order(order)
                .branch(branch)
                .cashier(cashier)
                .reason(refundDTO.getReason())
                .amount(refundDTO.getAmount())
                .paymentType(order.getPaymentType())
                .build();

        Refund savedRefund=refundRepository.save(refund);
        return RefundMapper.toDTO(savedRefund);
    }

    @Override
    public List<RefundDTO> getAllRefunds() throws Exception {
        return refundRepository.findAll().stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashierId(Long cashierId) throws Exception {
        return refundRepository.findByCashierId(cashierId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByShiftReport(Long shiftReportId) throws Exception {
        return refundRepository.findByShiftReportId(shiftReportId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashierIDAndDateRange(Long cashierId, LocalDateTime start, LocalDateTime end) throws Exception {
        return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId,start,end).stream().map(RefundMapper::toDTO).collect(Collectors.toList()) ;
    }

    @Override
    public List<Refund> getRefundByBranchId(Long branchId) throws Exception {
        return refundRepository.findByBranchId(branchId);
    }

    @Override
    public RefundDTO getRefundById(Long refundId) throws Exception {
        Refund refund= refundRepository.findById(refundId).orElseThrow(()->new RefundNotFound("Refund Data Missing"));
        return RefundMapper.toDTO(refund);
    }

    @Override
    public void deleteRefund(Long refundId) throws Exception {

        if(this.getRefundById(refundId)==null) return;
        refundRepository.deleteById(refundId);
    }
}
