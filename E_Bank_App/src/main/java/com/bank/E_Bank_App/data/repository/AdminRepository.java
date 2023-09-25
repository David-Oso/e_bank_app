package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByAppUser_Email(String email);
String email;
}
