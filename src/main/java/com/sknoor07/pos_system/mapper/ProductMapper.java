package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.payload.dto.ProductDTO;

public class ProductMapper {
    public static ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .image(product.getImage())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .storeId(product.getStore()!=null?product.getStore().getId():null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toProduct(ProductDTO productDTO, Store store) {
        return Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .image(productDTO.getImage())
                .mrp(productDTO.getMrp())
                .sellingPrice(productDTO.getSellingPrice())
                .brand(productDTO.getBrand())
                .build();
    }
}
