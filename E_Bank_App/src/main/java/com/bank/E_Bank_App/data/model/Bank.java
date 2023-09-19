package com.bank.E_Bank_App.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.bank.E_Bank_App.utils.E_BankUtils.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Bank {
    @Id
    private final String id = "E_bank587";
    private final String bankName = BANK_NAME;
    private final String email = BANK_EMAIL;
    private final String phoneNumber = BANK_PHONE_NUMBER;
    private final String location = BANK_LOCATION;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Customer> customers;
    private String banke;
}
