package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByAccountName(String name);
}
