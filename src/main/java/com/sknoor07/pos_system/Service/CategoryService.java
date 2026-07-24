package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception;

    List<CategoryDTO> getCategoriesByStore(Long storeId);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws Exception;

    void deleteCategory(Long id) throws Exception;

}
