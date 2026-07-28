package com.sknoor07.pos_system.modals.customer;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDateTime dateOfBirth;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
