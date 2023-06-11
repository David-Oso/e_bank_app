package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByEmail(String email);
}
