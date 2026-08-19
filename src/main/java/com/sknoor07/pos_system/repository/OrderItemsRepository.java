package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.orders.OrderItem;
import com.sknoor07.pos_system.payload.dto.branchAnalytics.CategorySalesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
            SELECT p.id, p.name, sum(oi.quantity) as quantity from OrderItem oi join oi.product as p JOIN oi.order as o
            WHERE o.branch.id = :branchId
            GROUP BY p.id,p.name
            ORDER BY quantity DESC
            """)
    List<Object[]> getTopProductByQuantity(@Param("branchId") Long branchId);

    @Query("""
            SELECT c.name, sum(oi.quantity *oi.price) as totalAmount, sum(oi.quantity) as quantitySold
            from OrderItem oi
            JOIN oi.product as p
            JOIN oi.order as o
            JOIN p.category as c
            WHERE o.branch.id = :branchId
            AND o.createdAt >= :start
            AND o.createdAt <= :end
            GROUP BY c.id,c.name
            ORDER BY totalAmount DESC
            """)
    List<Object[]> getCategoryWiseSales(@Param("branchId") Long branchId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    /*@Query("""
            SELECT
            new com.sknoor07.pos_system.payload.dto.branchAnalytics.CategorySalesDTO(p.category.name,sum(oi.quantity*p.sellingPrice),sum(oi.quantity))
            FROM OrderItem oi
            JOIN oi.product as p
            WHERE p.store.storeAdmin.id = :storeAdminId
            GROUP BY p.category.name
            """)
    List<CategorySalesDTO> getSalesGroupByCategory(@Param("storeAdminId") Long storeAdminId);*/
}
