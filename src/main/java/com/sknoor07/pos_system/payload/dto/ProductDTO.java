package com.sknoor07.pos_system.payload.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private long id;
    private String name;
    private String sku;
    private String description;
    private String image;
    //private Category category;
    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private Long storeId;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
