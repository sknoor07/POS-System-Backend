package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.store.StoreContact;
import com.sknoor07.pos_system.modals.store.StoreStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {

    private Long id;

    private String brandName;

    private UserDTO storeAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private String StoreType;

    private StoreStatus status;

    private StoreContact contact;
}
