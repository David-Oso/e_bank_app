package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
