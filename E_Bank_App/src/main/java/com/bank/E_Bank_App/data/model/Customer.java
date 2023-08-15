package com.bank.E_Bank_App.data.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private AppUser appUser;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Account account = new Account();
    private LocalDate dateOfBirth;
    private int age;
    private String imageUrl;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

}
