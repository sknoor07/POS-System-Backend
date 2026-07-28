package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.orders.OrderStatus;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.payload.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO order) throws Exception;
    OrderDTO getOrderById(Long orderId);
    //List<OrderDTO> getOrderByBranchId(Long branchId);
    List<OrderDTO>getOrdersByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus orderStatus);

    List<OrderDTO> getOrderByCashier(Long cashierId);

    void deleteOrder(Long OrderId);
    List<OrderDTO> getTodayOrdersByBranch(Long branchId);

    List<OrderDTO>getOrderByCustomerId(Long customerId);

    List<OrderDTO> getTop5RecentOrderByBranchId(Long branchId);


}
