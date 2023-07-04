package com.bank.E_Bank_App.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MyToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Customer customer;
    @Column(unique = true)
    private String token;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final LocalDateTime expirationTime = createdAt.plusMinutes(10L);
}
