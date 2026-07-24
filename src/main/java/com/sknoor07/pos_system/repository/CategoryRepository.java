package com.sknoor07.pos_system.repository;

import com.sknoor07.pos_system.modals.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStoreId(Long storeId);
}
