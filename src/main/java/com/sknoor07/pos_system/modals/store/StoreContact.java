package com.sknoor07.pos_system.modals.store;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreContact {
    private String address;
    private String phone;
    @Email(message= "Invalid email")
    private String email;
}
