package com.sknoor07.pos_system;

import com.sknoor07.pos_system.Service.Impl.OrderServiceImpl;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.orders.OrderItem;
import com.sknoor07.pos_system.modals.orders.OrderStatus;
import com.sknoor07.pos_system.modals.orders.PaymentType;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.OrderDTO;
import com.sknoor07.pos_system.payload.dto.OrderItemDTO;
import com.sknoor07.pos_system.repository.OrderItemsRepository;
import com.sknoor07.pos_system.repository.OrderRepository;
import com.sknoor07.pos_system.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User cashier;
    private Branch branch;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        branch = new Branch();
        branch.setId(1L);
        branch.setName("Main Branch");

        cashier = User.builder()
                .id(10L)
                .fullName("Test Cashier")
                .email("cashier@test.com")
                .password("password")
                .role(UserRole.ROLE_BRANCH_CASHIER)
                .branch(branch)
                .build();

        product1 = Product.builder()
                .id(100L)
                .name("Product A")
                .sku("SKU-A")
                .sellingPrice(25.0)
                .build();

        product2 = Product.builder()
                .id(200L)
                .name("Product B")
                .sku("SKU-B")
                .sellingPrice(15.0)
                .build();
    }

    @Test
    void createOrder_persistsParentBeforeChildren() throws Exception {
        // Arrange
        OrderItemDTO itemDTO1 = OrderItemDTO.builder()
                .productId(100L)
                .quantity(2)
                .build();
        OrderItemDTO itemDTO2 = OrderItemDTO.builder()
                .productId(200L)
                .quantity(3)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .paymentType(PaymentType.CASH)
                .orderItem(List.of(itemDTO1, itemDTO2))
                .build();

        when(userService.getCurrentUser()).thenReturn(cashier);
        when(productRepository.findById(100L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(200L)).thenReturn(Optional.of(product2));

        // Simulate orderRepository.save: assign an ID to the saved Order
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(1000L);
            return o;
        });

        // Simulate orderItemsRepository.save: assign IDs to saved OrderItems
        when(orderItemsRepository.save(any(OrderItem.class))).thenAnswer(invocation -> {
            OrderItem item = invocation.getArgument(0);
            if (item.getId() == null) {
                item.setId(item.getProduct().getId() + 5000L);
            }
            return item;
        });

        // Act
        OrderDTO result = orderService.createOrder(orderDTO);

        // Assert — parent Order is saved BEFORE any OrderItem
        InOrder inOrder = inOrder(orderRepository, orderItemsRepository);
        inOrder.verify(orderRepository).save(any(Order.class));           // parent first
        inOrder.verify(orderItemsRepository, times(2)).save(any(OrderItem.class)); // children after

        // Assert — the result contains the persisted Order's ID
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(1000L, result.getId());

        // Assert — each OrderItem was associated with the saved parent
        verify(orderItemsRepository, times(2)).save(argThat(item ->
                item.getOrder() != null && item.getOrder().getId() != null && item.getOrder().getId().equals(1000L)
        ));

        // Assert — total amount = (25*2) + (15*3) = 50 + 45 = 95
        assertEquals(0, new BigDecimal("95.0").compareTo(result.getTotalAmount()));

        // Assert — final save updates the total on the parent
        verify(orderRepository, times(2)).save(any(Order.class));
    }
}
