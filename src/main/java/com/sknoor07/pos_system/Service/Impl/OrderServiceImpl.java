package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.OrderService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.mapper.OrderMapper;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.orders.OrderItem;
import com.sknoor07.pos_system.modals.orders.OrderStatus;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.OrderDTO;
import com.sknoor07.pos_system.payload.dto.OrderItemDTO;
import com.sknoor07.pos_system.repository.OrderRepository;
import com.sknoor07.pos_system.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) throws Exception {
        User cashier= userService.getCurrentUser();
        Branch branch= cashier.getBranch();
        if(branch==null){
            throw new Exception("Cashier is Not Assigned to a branch");
        }
        Order order1= Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDTO.getCustomer())
                .paymentType(orderDTO.getPaymentType())
                .build();
        List<OrderItem> orderItems= orderDTO.getOrderItem().stream().map(item->{
            Product product= productRepository.findById(item.getProductId()).orElseThrow(()->new EntityNotFoundException("Product Id Missing in Order"));
            return OrderItem.builder().product(product).quantity(item.getQuantity()).price(product.getSellingPrice()*item.getQuantity()).order(order1).build();
        }).toList();
        double total= orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
        order1.setTotalAmount(total);
        order1.setOrderItems(orderItems);
        return OrderMapper.toDTO(orderRepository.save(order1));


    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        return OrderMapper.toDTO(orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order Not Found with id: "+orderId)));
    }



    @Override
    public List<OrderDTO> getOrdersByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus orderStatus) {
        return orderRepository.findByBranchId(branchId).stream()
                .filter(order -> customerId == null ||
                        (order.getCustomer() != null && order.getCustomer().getId().equals(customerId)))
                .filter(order -> cashierId == null ||
                        (order.getCashier() != null && order.getCashier().getId().equals(cashierId)))
                .filter(order -> paymentType == null ||
                        order.getPaymentType() == paymentType)
                .filter(order -> orderStatus == null ||
                        order.getOrderStatus() == orderStatus)
                .map(OrderMapper::toDTO)
                .toList();
    }


    @Override
    public List<OrderDTO> getOrderByCashier(Long cashierId) {
        return orderRepository.findByCashierId(cashierId).stream().map(OrderMapper::toDTO).toList();
    }

    @Override
    public void deleteOrder(Long OrderId) {
        Order order= orderRepository.findById(OrderId).orElseThrow(()->new EntityNotFoundException("Order Not Found with id: "+OrderId));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranch(Long branchId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start= today.atStartOfDay();
        LocalDateTime end= today.plusDays(1).atStartOfDay();
        return orderRepository.findByBranchIdAndCreatedAtBetween(branchId,start,end).stream().map(OrderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> getOrderByCustomerId(Long customerId) {

        return orderRepository.findByCustomerId(customerId).stream().map(OrderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> getTop5RecentOrderByBranchId(Long branchId) {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId).stream().map(OrderMapper::toDTO).toList();
    }
}
