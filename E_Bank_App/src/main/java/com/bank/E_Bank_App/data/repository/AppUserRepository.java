package com.bank.E_Bank_App.data.repository;

import com.bank.E_Bank_App.data.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmail(String email);
    AppUser findAppUserByFirstName(String firstName);
}
