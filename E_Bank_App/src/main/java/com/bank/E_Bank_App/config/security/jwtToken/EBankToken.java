package com.bank.E_Bank_App.config.security.jwtToken;

import com.bank.E_Bank_App.data.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EBankToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String token;
    private boolean isRevoked;
    private boolean isExpired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_jwt_id")
    private AppUser appUser;
}
