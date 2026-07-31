package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStoreId(Long storeId);
}
