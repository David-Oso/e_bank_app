package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAppUser_Email(String email);
    Optional<Customer> findByAppUser_Id(Long customerId);
    Optional<Customer> findByAccount_AccountNumber(String accountNumber);
}
