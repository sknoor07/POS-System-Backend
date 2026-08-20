package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.OrderService;
import com.sknoor07.pos_system.modals.orders.OrderStatus;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.payload.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "Endpoints for creating orders, processing checkout, retrieving order history, and filtering orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create Order", description = "Creates a new order/transaction with line items, applied taxes, discounts, and payment methods (Cash, Card, UPI, Stripe, Razorpay).")
    @ApiResponse(responseCode = "201", description = "Order successfully created")
    @PostMapping()
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }

    @Operation(summary = "Get Order by ID", description = "Retrieves full details of a specific order by order ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved order details")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(
            @Parameter(description = "ID of the order", required = true) @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @Operation(summary = "Get Orders by Branch", description = "Retrieves branch orders with optional filters by customer, cashier, payment type, or order status.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered branch orders")
    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getOrderByBranch(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId,
            @Parameter(description = "Optional customer ID filter") @RequestParam(required = false) Long customerId,
            @Parameter(description = "Optional cashier ID filter") @RequestParam(required = false) Long cashierId,
            @Parameter(description = "Optional payment type filter (e.g. CASH, CARD, UPI)") @RequestParam(required = false) PaymentType paymentType,
            @Parameter(description = "Optional order status filter (e.g. COMPLETED, PENDING, CANCELLED)") @RequestParam(required = false) OrderStatus orderStatus) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByBranch(branchId, customerId, cashierId, paymentType, orderStatus));
    }

    @Operation(summary = "Get Orders by Cashier ID", description = "Retrieves all orders processed by a specific cashier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved cashier orders")
    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDTO>> getOrderByCashierId(
            @Parameter(description = "ID of the cashier", required = true) @PathVariable Long cashierId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByCashier(cashierId));
    }

    @Operation(summary = "Get Today's Branch Orders", description = "Retrieves all orders created today for a specific branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved today's branch orders")
    @GetMapping("/today/branch/{id}")
    public ResponseEntity<List<OrderDTO>> getTodayOrder(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getTodayOrdersByBranch(id));
    }

    @Operation(summary = "Get Customer Orders", description = "Retrieves purchase history for a specific customer.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved customer order history")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getCustomerOrder(
            @Parameter(description = "ID of the customer", required = true) @PathVariable Long customerId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByCustomerId(customerId));
    }

    @Operation(summary = "Get Recent Orders by Branch", description = "Retrieves the 5 most recent orders created at a branch.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved top 5 recent orders")
    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDTO>> getRecentOrder(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getTop5RecentOrderByBranchId(branchId));
    }
}
