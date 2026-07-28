package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.OrderService;
import com.sknoor07.pos_system.modals.orders.OrderStatus;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.payload.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws Exception {
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO>getOrderById(@PathVariable Long id) throws Exception {
    return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getOrderByBranch(@PathVariable Long branchId,
                                                           @RequestParam(required = false) Long customerId,
                                                           @RequestParam(required = false) Long cashierId,
                                                           @RequestParam(required = false)PaymentType paymentType,
                                                           @RequestParam(required = false) OrderStatus orderStatus
                                                    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByBranch(branchId,customerId,cashierId,paymentType,orderStatus));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDTO>> getOrderByCashierId(@PathVariable Long cashierId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByCashier(cashierId));
    }

    @GetMapping("/today/branch/{id}")
    public ResponseEntity<List<OrderDTO>> getTodayOrder(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getTodayOrdersByBranch(id));
    }

        @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getCustomerOrder(@PathVariable Long customerId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByCustomerId(customerId));
    }

    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDTO>> getRecentOrder(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getTop5RecentOrderByBranchId(branchId));
    }
}
