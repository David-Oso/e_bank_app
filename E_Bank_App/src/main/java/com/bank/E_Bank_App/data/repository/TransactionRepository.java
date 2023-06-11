package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
