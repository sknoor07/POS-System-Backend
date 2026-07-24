package com.sknoor07.pos_system.modals.user;

import com.sknoor07.pos_system.modals.store.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @ManyToOne
    private Store store;

    @Column(nullable = false,unique = true)
    @Email(message = "Enter a Valid Email")
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdAt, updatedAt, lastLoginAt;

}
