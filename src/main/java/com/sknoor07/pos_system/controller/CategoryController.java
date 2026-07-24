package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.CategoryService;
import com.sknoor07.pos_system.payload.dto.CategoryDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDTO));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDTO>> getCategoryByStoreId(@PathVariable Long storeId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoriesByStore(storeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(id,categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Category Deleted"));
    }
}
