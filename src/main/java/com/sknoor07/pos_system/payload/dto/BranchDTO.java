package com.sknoor07.pos_system.payload.dto;

import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class BranchDTO {

    private Long id;

    private String name;

    private String address;

    private Long phone;

    private String email;

    private Set<String> workingDays;

    private LocalTime openTime;

    private LocalTime closeTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long storeId;

}
