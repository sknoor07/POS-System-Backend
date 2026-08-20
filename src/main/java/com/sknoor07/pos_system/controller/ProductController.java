package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.ProductService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.ProductDTO;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Endpoints for creating, updating, searching, retrieving, and deleting catalog products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @Operation(summary = "Create Product", description = "Creates a new catalog product (SKU, price, barcode, category, image) for a store.")
    @ApiResponse(responseCode = "201", description = "Product successfully created")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDTO,
            @Parameter(description = "Bearer JWT token", required = true) @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.getUserFromJwtToken(token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO, user));
    }

    @Operation(summary = "Get Products by Store ID", description = "Retrieves all products belonging to a specific store catalog.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store products catalog")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDTO>> getProductByStoreId(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long storeId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByStoreId(storeId));
    }

    @Operation(summary = "Update Product", description = "Updates an existing product's details (name, price, SKU, image, category) by product ID.")
    @ApiResponse(responseCode = "200", description = "Product successfully updated")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestBody ProductDTO productDTO,
            @Parameter(description = "ID of the product", required = true) @PathVariable Long id,
            @Parameter(description = "Bearer JWT token", required = true) @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.getUserFromJwtToken(token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, productDTO, user));
    }

    @Operation(summary = "Delete Product", description = "Deletes a product by product ID.")
    @ApiResponse(responseCode = "200", description = "Product successfully deleted")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteProduct(
            @Parameter(description = "ID of the product", required = true) @PathVariable Long id,
            @Parameter(description = "Bearer JWT token", required = true) @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.getUserFromJwtToken(token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        productService.deleteProduct(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Product deleted successfully"));
    }

    @Operation(summary = "Search Products by Keyword", description = "Searches store products by name, SKU, or description keyword.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching products")
    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchProductByKeyword(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long storeId,
            @Parameter(description = "Search keyword (name, SKU)", required = true) @RequestParam String keyword,
            @Parameter(description = "Bearer JWT token", required = true) @RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductsByKeyword(storeId, keyword));
    }
}
