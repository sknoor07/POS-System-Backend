package com.sknoor07.pos_system.modals.branch;

import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    private Long phone;

    private String email;

    @ElementCollection
    private Set<String> workingDays;

    private LocalTime openTime;

    private LocalTime closeTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private Store store;

    @OneToOne
    private User userManager;


    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return "Branch [id=" + id + ", name=" + name + ", address=" + address +"]";
    }


}
