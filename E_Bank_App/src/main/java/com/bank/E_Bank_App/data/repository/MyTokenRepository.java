package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.MyToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyTokenRepository extends JpaRepository<MyToken, Long> {
    Optional<MyToken> findMyTokenByCustomer(Customer customer);
    Optional<MyToken> findByToken(String token);
}
