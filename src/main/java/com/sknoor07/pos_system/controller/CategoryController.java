package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.CategoryService;
import com.sknoor07.pos_system.payload.dto.CategoryDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
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
@RequestMapping("/api/categories")
@Tag(name = "Category Management", description = "Endpoints for creating, retrieving, updating, and deleting product categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create Category", description = "Creates a new product category for a store.")
    @ApiResponse(responseCode = "201", description = "Category successfully created")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDTO));
    }

    @Operation(summary = "Get Categories by Store ID", description = "Retrieves all product categories belonging to a specific store.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories list")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDTO>> getCategoryByStoreId(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long storeId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoriesByStore(storeId));
    }

    @Operation(summary = "Update Category", description = "Updates an existing category by category ID.")
    @ApiResponse(responseCode = "200", description = "Category successfully updated")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @RequestBody CategoryDTO categoryDTO,
            @Parameter(description = "ID of the category", required = true) @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(id, categoryDTO));
    }

    @Operation(summary = "Delete Category", description = "Deletes a category by category ID.")
    @ApiResponse(responseCode = "200", description = "Category successfully deleted")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteCategory(
            @Parameter(description = "ID of the category", required = true) @PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Category Deleted"));
    }
}
