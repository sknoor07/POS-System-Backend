package com.sknoor07.pos_system.repository;


import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.payload.dto.branchAnalytics.CategorySalesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStoreId(Long storeId);

    @Query("""
    SELECT p FROM Product p
    WHERE p.store.id = :storeId
    AND (
        LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
""")
    List<Product> searchByKeyword(@Param(value = "storeId") Long storeid,
                                  @Param(value = "keyword") String keyword
    );

    @Query("""
        SELECT count(p) FROM Product p
        WHERE p.store.storeAdmin.id = :storeAdminId
""")
    int countByStoreAdminId(@Param("storeAdminId") Long storeAdminId);

    @Query("""
        SELECT p.name FROM Product p
        WHERE p.store.storeAdmin.id = :storeAdminId
        AND p.id NOT IN (SELECT i.product.id from Inventory i where i.quantity>5)
""")
    List<String> findLowStockProduct(@Param("storeAdminId") Long storeAdminId);

    /*@Query("""

""")
    List<CategorySalesDTO> getSalesGroupedBYCategory(@Param("storeAdminId") Long storeAdminId);
*/
}
