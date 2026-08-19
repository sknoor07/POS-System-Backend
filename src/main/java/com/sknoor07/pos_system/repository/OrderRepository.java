package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.orders.Order;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.storeAnalytics.PaymentInsightDTO;
import com.sknoor07.pos_system.payload.dto.storeAnalytics.TimeSeriesPointDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByBranchId(Long branchId);
    List<Order> findByCashierId(Long caseId);
    List<Order> findByBranchIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(Long branchId, LocalDateTime from, LocalDateTime to);
    List<Order> findByCashierAndCreatedAtBetween(User cashier, LocalDateTime from, LocalDateTime to);
    List<Order> findTop5ByBranchIdOrderByCreatedAtDesc(Long branchId);

    //branchAnalyticsService
    @Query("""
            SELECT sum(o.totalAmount) 
            from Order o
            where o.branch.id = :branchId 
            AND 
            o.createdAt BETWEEN :start and :end
        """)
    Optional<BigDecimal> getTotalSalesBetween(@Param("branchId") Long branchId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query("""
        SELECT c.id,c.fullName,Sum(o.totalAmount),count(o) as totalRevenue
        FROM Order as o
        JOIN o.cashier as c
        where o.branch.id=:branchId
        group by c.id,c.fullName
        order by totalRevenue DESC
    """)
    List<Object[]> getTopCashierByRevenue(@Param("branchId") Long branchId);

    @Query("""
            SELECT count(o) from Order o
            where o.branch.id = :branchId
            AND DATE(o.createdAt)= :date
        """)
    int countOrdersByBranchAndDate(@Param("branchId") Long branchId, @Param("date") LocalDate date);


    @Query("""
            SELECT count(distinct o.cashier.id) from Order o
            where o.branch.id = :branchId
            AND DATE(o.createdAt)= :date
        """)
    int countDistinctCashierByBranchAndDate(@Param("branchId") Long branchId,@Param("date") LocalDate date);

    @Query("""
        SELECT
        o.paymentType,sum(o.totalAmount),count(o)
        FROM Order o
        WHERE o.branch.id = :branchId
        AND DATE(o.createdAt)= :date
        GROUP BY o.paymentType
        """)
    List<Object[]> getPaymentBreakdownByMethod(@Param("branchId") Long branchId, @Param("date") LocalDate date);

    @Query("""
       SELECT sum(o.totalAmount)
       From Order as o
       WHERE o.branch.store.storeAdmin.id=:storeAdminId
       """)
    Optional<Double> sumTotalSalesByStoreAdmin(@Param("storeAdminId") Long storeAdminId);

    @Query("""
       SELECT count(o)
       From Order as o
       WHERE o.branch.store.storeAdmin.id=:storeAdminId
       """)
    Optional<Double> countByStoreAdmin(@Param("storeAdminId") Long storeAdminId);

    @Query("""
        SELECT o FROM Order o 
        WHERE o.branch.store.storeAdmin.id=:storeAdminId
        AND DATE(o.createdAt) BETWEEN :start AND :end
        """)
    List<Order> finaAllByStoreAdminAndCreatedAtBetween(@Param("storeAdminId") Long storeAdminId, @Param("start") LocalDateTime from, @Param("end") LocalDateTime to);

    @Query("""
        SELECT
        new com.sknoor07.pos_system.payload.dto.storeAnalytics.TimeSeriesPointDTO(o.createdAt,sum(o.totalAmount))
        FROM Order o
        WHERE o.branch.store.storeAdmin.id=:storeAdminId
        AND DATE(o.createdAt) BETWEEN :start AND :end
        GROUP BY o.createdAt
        ORDER BY o.createdAt
        """)
    List<TimeSeriesPointDTO> getDailySales(@Param("storeAdminId") Long storeAdminId, @Param("start") LocalDateTime from, @Param("end") LocalDateTime to);

    @Query("""
        SELECT
        new com.sknoor07.pos_system.payload.dto.storeAnalytics.PaymentInsightDTO(o.paymentType,sum(o.totalAmount))
        FROM Order o
        WHERE o.branch.store.storeAdmin.id=:storeAdminId
        GROUP BY o.paymentType
        """)
    List<PaymentInsightDTO> getSalesByPaymentMethod(@Param("storeAdminId") Long storeAdminId);
}
