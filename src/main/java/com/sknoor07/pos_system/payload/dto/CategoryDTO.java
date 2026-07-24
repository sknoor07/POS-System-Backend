package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.store.Store;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private Long id;

    private String name;

    private Long storeId;
}
