package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.category.Category;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.payload.dto.CategoryDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class CategoryMapper {
    public static CategoryDTO tocategoryDTO(Category category) {
        return  CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore().getId())
                .build();
    }
}
