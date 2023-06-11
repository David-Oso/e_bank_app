package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, String> {
}
